
package controllers.hacker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CurriculumService;
import services.EducationDataService;
import services.PersonalDataService;
import domain.PersonalData;
import forms.CreateCurriculumForm;

@Controller
@RequestMapping("/personalData/hacker")
public class PersonalDataHackerController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private PersonalDataService		personalDataService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataId) {
		ModelAndView result;
		final CreateCurriculumForm form;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.personalDataService.exist(personalDataId);

		if (!(personalDataId != 0 && exist)) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			form = this.personalDataService.creteForm(personalDataId);

			final Boolean security = this.personalDataService.security(personalDataId);

			if (security)
				result = this.createEditModelAndView(form);
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("personalData") final CreateCurriculumForm form, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final PersonalData personalReconstruct = this.personalDataService.reconstruct(form, binding);

		final Boolean security = this.personalDataService.security(personalReconstruct.getId());

		if (security) {

			if (binding.hasErrors())
				result = this.createEditModelAndView(form);
			else
				try {
					this.personalDataService.save(personalReconstruct);
					result = new ModelAndView("redirect:/curriculum/hacker/list.do");

				} catch (final Throwable oops) {
					result = this.createEditModelAndView(form, "curriculum.commit.error");
				}

		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		result.addObject("banner", banner);

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final CreateCurriculumForm createCurriculumForm) {
		ModelAndView result;

		result = this.createEditModelAndView(createCurriculumForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateCurriculumForm createCurriculumForm, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("curriculum/editEducationData");
		result.addObject("createCurriculumForm", createCurriculumForm);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}

}
