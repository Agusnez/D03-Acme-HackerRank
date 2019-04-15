
package controllers.hacker;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CompanyService;
import services.ConfigurationService;
import services.CurriculumService;
import services.HackerService;
import services.PositionService;
import domain.Application;
import domain.Curriculum;
import domain.Hacker;
import forms.ApplicationForm;

@Controller
@RequestMapping("/application/hacker")
public class ApplicationHackerController {

	// Services ---------------------------------------------------

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private PositionService			positionService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Application application;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.applicationService.existApplication(applicationId);

		if (exist) {

			final Boolean security = this.applicationService.securityHacker(applicationId);

			if (security) {

				application = this.applicationService.findOne(applicationId);

				result = new ModelAndView("application/display");
				result.addObject("application", application);
				result.addObject("banner", banner);

			} else
				result = new ModelAndView("redirect:/application/hacker/list.do");
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Application> applicationAccepted;
		Collection<Application> applicationRejected;
		Collection<Application> applicationSubmitted;
		Collection<Application> applicationPending;

		final Hacker hacker = this.hackerService.findByPrincipal();

		applicationAccepted = this.applicationService.findAllAcceptedByHacker(hacker.getId());
		applicationRejected = this.applicationService.findAllRejectedByHacker(hacker.getId());
		applicationSubmitted = this.applicationService.findAllSubmittedByHacker(hacker.getId());
		applicationPending = this.applicationService.findAllPendingByHacker(hacker.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("application/list");
		result.addObject("applicationAccepted", applicationAccepted);
		result.addObject("applicationRejected", applicationRejected);
		result.addObject("applicationSubmitted", applicationSubmitted);
		result.addObject("applicationPending", applicationPending);
		result.addObject("banner", banner);
		result.addObject("requestURI", "application/hacker/list.do");

		return result;
	}

	@RequestMapping(value = "/listObsoletes", method = RequestMethod.GET)
	public ModelAndView listObsoletes() {
		final ModelAndView result;
		Collection<Application> applicationObsoletes;

		final Hacker hacker = this.hackerService.findByPrincipal();

		applicationObsoletes = this.applicationService.findAllDeadLinePastByHacker(hacker.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("application/listObsoletes");
		result.addObject("applicationObsoletes", applicationObsoletes);
		result.addObject("banner", banner);
		result.addObject("requestURI", "application/hacker/listObsoletes.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int positionId) {
		ModelAndView result;
		final ApplicationForm applicationForm = new ApplicationForm();

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.positionService.exist(positionId);

		final Date now = new Date(System.currentTimeMillis() - 1000);

		if (exist && this.positionService.findOne(positionId).getDeadline().after(now)) {

			applicationForm.setPosition(positionId);
			result = this.createEditModelAndView(applicationForm);

		} else {

			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Boolean security;

		final Boolean exist = this.applicationService.existApplication(applicationId);

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (exist) {

			security = this.applicationService.securityHacker(applicationId);
			final Application application = this.applicationService.findOne(applicationId);

			final Date now = new Date(System.currentTimeMillis() - 1000);

			if (security && (application.getStatus().equals("SUBMITTED") || application.getStatus().equals("PENDING")) && application.getPosition().getDeadline().after(now)) {

				final ApplicationForm applicationForm = this.applicationService.editForm(application);

				result = this.createEditModelAndView(applicationForm, null);
			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {

			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute(value = "application") final ApplicationForm applicationForm, final BindingResult binding) {
		ModelAndView result;
		Application application = null;

		final Boolean security = this.applicationService.securityHacker(applicationForm.getId());
		final Boolean exist = this.positionService.exist(applicationForm.getPosition());

		if (security && exist)
			application = this.applicationService.reconstruct(applicationForm, binding);

		final Date now = new Date(System.currentTimeMillis() - 1000);

		if (exist && security && (application.getStatus().equals("SUBMITTED") || application.getStatus().equals("PENDING")) && application.getPosition().getDeadline().after(now) && application.getPosition().getFinalMode()) {

			if (binding.hasErrors())
				result = this.createEditModelAndView(applicationForm, null);
			else
				try {

					final Curriculum curriculum = application.getCurriculum();

					final Curriculum copy = this.curriculumService.copyCurriculum(curriculum);

					application.setCurriculum(copy);

					this.applicationService.save(application);
					result = new ModelAndView("redirect:/application/hacker/list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(applicationForm, "application.commit.error");

				}

		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}
	// Ancillary methods

	protected ModelAndView createEditModelAndView(final ApplicationForm applicationForm) {
		ModelAndView result;

		result = this.createEditModelAndView(applicationForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ApplicationForm applicationForm, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Collection<Curriculum> curriculums = this.curriculumService.findByHackerId(this.hackerService.findByPrincipal().getId());

		final Map<Integer, String> curriculumsMap = new HashMap<>();

		for (final Curriculum curriculum : curriculums)
			curriculumsMap.put(curriculum.getId(), curriculum.getPersonalData().getStatement());

		result = new ModelAndView("application/edit");
		result.addObject("application", applicationForm);
		result.addObject("curriculumsMap", curriculumsMap);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}
}
