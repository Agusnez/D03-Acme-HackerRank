
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Hacker;

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

			this.actorService.checkEmail(hacker.getEmail(), false);
			this.actorService.checkPhone(hacker.getPhone());

			final String phone = this.actorService.checkPhone(hacker.getPhone());
			hacker.setPhone(phone);

			result = this.hackerRepository.save(hacker);

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

}
