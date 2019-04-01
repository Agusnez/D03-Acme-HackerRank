
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Company;
import forms.RegisterCompanyForm;

@Service
@Transactional
public class CompanyService {

	// Managed repository
	@Autowired
	private CompanyRepository	companyRepository;

	// Suporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods

	public Company create() {

		Company result;
		result = new Company();

		final UserAccount userAccount = this.userAccountService.createCompany();
		result.setUserAccount(userAccount);

		result.setSpammer(null);

		return result;

	}

	public Collection<Company> findAll() {

		Collection<Company> result;
		result = this.companyRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Company findOne(final int companyId) {

		Assert.notNull(companyId);
		Company result;
		result = this.companyRepository.findOne(companyId);
		return result;
	}

	public Company save(final Company company) {
		Assert.notNull(company);
		Company result;

		if (company.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == company.getId() || actor.getUserAccount().getAuthorities().contains(admin));

			this.actorService.checkEmail(company.getEmail(), false);
			this.actorService.checkPhone(company.getPhone());

			final String phone = this.actorService.checkPhone(company.getPhone());
			company.setPhone(phone);

			result = this.companyRepository.save(company);

		} else {

			String pass = company.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = company.getUserAccount();
			userAccount.setPassword(pass);

			company.setUserAccount(userAccount);

			this.actorService.checkEmail(company.getEmail(), false);
			this.actorService.checkPhone(company.getPhone());

			final String phone = this.actorService.checkPhone(company.getPhone());
			company.setPhone(phone);

			result = this.companyRepository.save(company);

		}
		return result;

	}
	//Other business methods ----------------------------

	public Company findByPrincipal() {
		Company company;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		company = this.findByUserAccount(userAccount);
		Assert.notNull(company);

		return company;
	}

	public Company findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Company result;

		result = this.companyRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	//Recontruct para registrar Company
	public Company reconstruct(final RegisterCompanyForm form, final BindingResult binding) {

		this.validator.validate(form, binding);

		final Company company = this.create();

		company.setCommercialName(form.getCommercialName());
		company.setName(form.getName());
		company.setSurnames(form.getSurnames());
		company.setVat(form.getVat());
		company.setPhoto(form.getPhoto());
		company.setEmail(form.getEmail());
		company.setPhone(form.getPhone());
		company.setAddress(form.getAddress());
		company.setCreditCard(form.getCreditCard());
		company.getUserAccount().setUsername(form.getUsername());
		company.getUserAccount().setPassword(form.getPassword());

		return company;

	}

}