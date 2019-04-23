
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import security.Authority;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Hacker;
import forms.ApplicationForm;

@Service
@Transactional
public class ApplicationService {

	// Managed Repository ------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods -----------------------

	public ApplicationForm create() {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HACKER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		ApplicationForm result;
		result = new ApplicationForm();
		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result;
		result = this.applicationRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Application findOne(final int applicationId) {
		Application application;
		application = this.applicationRepository.findOne(applicationId);
		Assert.notNull(application);
		return application;
	}

	public Application save(final Application application) {

		Assert.notNull(application);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HACKER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Application res = null;

		res = this.applicationRepository.save(application);

		return res;

	}

	public Application accept(final int applicationId) {

		Application res = null;

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		res = this.findOne(applicationId);
		res.setStatus("ACCEPTED");
		this.applicationRepository.save(res);

		this.messageService.notificationApplicationStatus(res);

		return res;
	}

	public Application reject(final int applicationId) {

		Application res = null;

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		res = this.findOne(applicationId);
		res.setStatus("REJECTED");
		this.applicationRepository.save(res);

		this.messageService.notificationApplicationStatus(res);

		return res;
	}

	public void delete(final Application application) {

		this.applicationRepository.delete(application);
	}

	public void deleteAll(final int actorId) {

		final Collection<Application> apps = this.findByHackerId(actorId);

		if (!apps.isEmpty())
			for (final Application a : apps)
				this.applicationRepository.delete(a);
	}

	public Collection<Application> findByHackerId(final int hackerId) {

		final Collection<Application> result = this.applicationRepository.findByHackerId(hackerId);

		return result;
	}

	public Collection<Application> findAllAcceptedByHacker(final int hackerId) {
		Collection<Application> result;
		result = this.applicationRepository.findAllAcceptedByHacker(hackerId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Application> findAllRejectedByHacker(final int hackerId) {
		Collection<Application> result;
		result = this.applicationRepository.findAllRejectedByHacker(hackerId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Application> findAllPendingByHacker(final int hackerId) {
		Collection<Application> result;
		result = this.applicationRepository.findAllPendingByHacker(hackerId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Application> findAllSubmittedByHacker(final int hackerId) {
		Collection<Application> result;
		result = this.applicationRepository.findAllSubmittedByHacker(hackerId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Application> findAllDeadLinePastByHacker(final int hackerId) {
		Collection<Application> result;
		result = this.applicationRepository.findAllDeadLinePastByHacker(hackerId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Application> findAllAcceptedByCompany(final int companyId) {
		Collection<Application> result;
		result = this.applicationRepository.findAllAcceptedByCompany(companyId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Application> findAllRejectedByCompany(final int companyId) {
		Collection<Application> result;
		result = this.applicationRepository.findAllRejectedByCompany(companyId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Application> findAllPendingByCompany(final int companyId) {
		Collection<Application> result;
		result = this.applicationRepository.findAllPendingByCompany(companyId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Application> findAllSubmittedByCompany(final int companyId) {
		Collection<Application> result;
		result = this.applicationRepository.findAllSubmittedByCompany(companyId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Application> findAllDeadLinePastByCompany(final int companyId) {
		Collection<Application> result;
		result = this.applicationRepository.findAllDeadLinePastByCompany(companyId);
		Assert.notNull(result);
		return result;
	}

	public Boolean ApplicationSecurity(final int applicationId) {
		Boolean res = false;

		final Hacker owner = this.findOne(applicationId).getHacker();
		final Company owner2 = this.findOne(applicationId).getPosition().getCompany();

		final Actor login = this.actorService.findByPrincipal();

		if (login.equals(owner) || login.equals(owner2))
			res = true;

		return res;
	}

	public Boolean existApplication(final int applicationId) {
		Boolean res = false;

		final Application app = this.applicationRepository.findOne(applicationId);

		if (app != null)
			res = true;

		return res;
	}

	public Application reconstruct(final ApplicationForm applicationForm, final BindingResult binding) {

		final Application res = new Application();

		final Date now = new Date(System.currentTimeMillis() - 1000);

		res.setAnswer(applicationForm.getAnswer());
		res.setCurriculum(this.curriculumService.findOne(applicationForm.getCurriculum()));
		res.setPosition(this.positionService.findOne(applicationForm.getPosition()));
		res.setId(applicationForm.getId());
		res.setVersion(applicationForm.getVersion());

		if (applicationForm.getId() == 0) {

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);
			final Authority authority = new Authority();
			authority.setAuthority(Authority.HACKER);
			Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

			res.setHacker(this.hackerService.findByPrincipal());
			res.setMoment(now);

			if (!res.getAnswer().isEmpty()) {

				res.setSubmitMoment(now);
				res.setStatus("SUBMITTED");

			} else
				res.setStatus("PENDING");

		} else {

			final Application oldOne = this.applicationRepository.findOne(applicationForm.getId());

			res.setMoment(oldOne.getMoment());
			res.setHacker(oldOne.getHacker());

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);
			final Authority authority = new Authority();
			authority.setAuthority(Authority.HACKER);
			final Authority authority2 = new Authority();
			authority2.setAuthority(Authority.COMPANY);
			Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority) || actor.getUserAccount().getAuthorities().contains(authority2)));

			if (!res.getAnswer().isEmpty()) {

				res.setStatus("SUBMITTED");
				res.setSubmitMoment(now);

			} else {

				res.setStatus("PENDING");
				res.setSubmitMoment(null);

			}

		}

		this.validator.validate(res, binding);

		return res;

	}

	public Boolean securityHacker(final int applicationId) {

		Boolean res = false;

		final Hacker hacker = this.hackerService.findByPrincipal();

		if (applicationId != 0) {

			final Application application = this.applicationRepository.findOne(applicationId);

			if (application != null) {
				final Hacker owner = application.getHacker();

				if (owner.equals(hacker))
					res = true;
			}

		} else
			res = true;

		return res;
	}

	public Boolean securityCompany(final int applicationId) {

		Boolean res = false;

		final Company company = this.companyService.findByPrincipal();

		final Application application = this.findOne(applicationId);

		final Company owner = application.getPosition().getCompany();

		if (owner.equals(company))
			res = true;

		return res;
	}

	public ApplicationForm editForm(final Application application) {

		final ApplicationForm res = new ApplicationForm();

		res.setAnswer(application.getAnswer());
		res.setCurriculum(application.getCurriculum().getId());
		res.setId(application.getId());
		res.setPosition(application.getPosition().getId());
		res.setVersion(application.getVersion());

		return res;
	}

	public Collection<Application> findByPositionId(final int positionId) {

		final Collection<Application> result = this.applicationRepository.findByPositionId(positionId);

		return result;
	}

}
