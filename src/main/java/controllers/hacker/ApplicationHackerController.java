
package controllers.hacker;

import java.util.Collection;

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
import services.HackerService;
import domain.Application;
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

				result = new ModelAndView("application/displayApplication");
				result.addObject("application", application);
				result.addObject("banner", banner);

			} else
				result = new ModelAndView("redirect:/list.do");
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

		result = new ModelAndView("application/listApplication");
		result.addObject("applicationAccepted", applicationAccepted);
		result.addObject("applicationRejected", applicationRejected);
		result.addObject("applicationSubmitted", applicationSubmitted);
		result.addObject("applicationPending", applicationPending);
		result.addObject("banner", banner);
		result.addObject("requestURI", "application/hacker/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final ApplicationForm applicationForm = new ApplicationForm();

		result = this.createEditModelAndView(applicationForm);

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

			if (security) {

				final Application application = this.applicationService.findOne(applicationId);

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

		final Application application = this.applicationService.reconstruct(applicationForm, binding);

		final Boolean security = this.applicationService.securityHacker(application.getId());

		if (security) {

			if (binding.hasErrors())
				result = this.createEditModelAndView(applicationForm, null);
			else
				try {
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

		result = new ModelAndView("application/createApplication");
		result.addObject("application", applicationForm);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}
}
