
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Finder;
import domain.Hacker;
import forms.RegisterHackerForm;

@Service
@Transactional
public class HackerService {

	// Managed repository
	@Autowired
	private HackerRepository	hackerRepository;

	// Suporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods

	public Hacker create() {

		Hacker result;
		result = new Hacker();

		final UserAccount userAccount = this.userAccountService.createHacker();
		result.setUserAccount(userAccount);

		result.setSpammer(null);

		return result;

	}

	public Collection<Hacker> findAll() {

		Collection<Hacker> result;
		result = this.hackerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Hacker findOne(final int hackerId) {

		Assert.notNull(hackerId);
		Hacker result;
		result = this.hackerRepository.findOne(hackerId);
		return result;
	}

	public Hacker save(final Hacker hacker) {
		Assert.notNull(hacker);
		Hacker result;

		if (hacker.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == hacker.getId() || actor.getUserAccount().getAuthorities().contains(admin));

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(hacker.getCreditCard().getExpYear() - 1900 >= now.getYear());
			Assert.isTrue(hacker.getCreditCard().getExpMonth() - 1 >= now.getMonth() || hacker.getCreditCard().getExpYear() - 1900 > now.getYear());

			this.actorService.checkEmail(hacker.getEmail(), false);
			this.actorService.checkPhone(hacker.getPhone());

			final String phone = this.actorService.checkPhone(hacker.getPhone());
			hacker.setPhone(phone);

			result = this.hackerRepository.save(hacker);

		} else {

			String pass = hacker.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = hacker.getUserAccount();
			userAccount.setPassword(pass);

			hacker.setUserAccount(userAccount);

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(hacker.getCreditCard().getExpYear() - 1900 >= now.getYear());
			Assert.isTrue(hacker.getCreditCard().getExpMonth() - 1 >= now.getMonth() || hacker.getCreditCard().getExpYear() - 1900 > now.getYear());

			this.actorService.checkEmail(hacker.getEmail(), false);
			this.actorService.checkPhone(hacker.getPhone());

			final String phone = this.actorService.checkPhone(hacker.getPhone());
			hacker.setPhone(phone);

			result = this.hackerRepository.save(hacker);

			final Finder finder = this.finderService.create();
			finder.setHacker(result);
			this.finderService.save(finder);

		}
		return result;

	}
	//Other business methods ----------------------------

	public Hacker findByPrincipal() {
		Hacker hacker;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		hacker = this.findByUserAccount(userAccount);
		Assert.notNull(hacker);

		return hacker;
	}

	public Hacker findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Hacker result;

		result = this.hackerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	//Recontruct para registrar Hacker
	public Hacker reconstruct(final RegisterHackerForm form, final BindingResult binding) {

		this.validator.validate(form, binding);

		final Hacker hacker = this.create();

		hacker.setName(form.getName());
		hacker.setSurnames(form.getSurnames());
		hacker.setVat(form.getVat());
		hacker.setPhoto(form.getPhoto());
		hacker.setEmail(form.getEmail());
		hacker.setPhone(form.getPhone());
		hacker.setAddress(form.getAddress());
		hacker.setCreditCard(form.getCreditCard());
		hacker.getUserAccount().setUsername(form.getUsername());
		hacker.getUserAccount().setPassword(form.getPassword());

		return hacker;

	}

	//Reconstruct para editar Hacker

	public Hacker reconstruct(final Hacker hacker, final BindingResult binding) {

		final Hacker result;

		final Hacker hackerBBDD = this.findOne(hacker.getId());

		if (hackerBBDD != null) {

			hacker.setUserAccount(hackerBBDD.getUserAccount());
			hacker.setSpammer(hackerBBDD.getSpammer());

			this.validator.validate(hacker, binding);

		}
		result = hacker;
		return result;

	}

	public void flush() {
		this.hackerRepository.flush();
	}

}
