
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CurriculumService;
import services.HackerService;
import services.PersonalDataService;
import domain.Curriculum;
import domain.PersonalData;
import forms.CreateCurriculumForm;

@Controller
@RequestMapping("/curriculum/hacker")
public class CurriculumHackerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private PersonalDataService		personalDataService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreateCurriculumForm createCurriculumForm;

		createCurriculumForm = new CreateCurriculumForm();

		result = this.createEditModelAndView(createCurriculumForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("curriculum") final CreateCurriculumForm form, final BindingResult binding) {
		ModelAndView result;

		final Curriculum curriculumReconstruct = this.curriculumService.reconstruct(form, binding);
		final PersonalData personalReconstruct = this.personalDataService.reconstruct(form, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(form);
		else
			try {
				final Curriculum c = this.curriculumService.save(curriculumReconstruct);
				final PersonalData p = this.personalDataService.save(personalReconstruct);
				c.setPersonalData(p);
				this.curriculumService.save(c);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(form, "actor.commit.error");
			}
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

		result = new ModelAndView("administrator/signUpAdministrator");
		result.addObject("curriculum", createCurriculumForm);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}

}
