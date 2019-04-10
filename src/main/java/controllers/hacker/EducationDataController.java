
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
import controllers.AbstractController;
import domain.Curriculum;
import domain.EducationData;
import forms.EducationDataForm;

@Controller
@RequestMapping("/educationData/hacker")
public class EducationDataController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		EducationDataForm educationData;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.curriculumService.exist(curriculumId);

		if (exist) {
			final Boolean security = this.curriculumService.security(curriculumId);

			if (security) {
				educationData = new EducationDataForm();

				educationData.setCurriculumId(curriculumId);

				result = this.createEditModelAndView(educationData);
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("educationData") final EducationDataForm form, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final EducationData educationReconstruct = this.educationDataService.reconstruct(form, binding);

		final Boolean existCurriculum = this.curriculumService.exist(form.getCurriculumId());

		final Boolean existData = this.educationDataService.exist(form.getId());

		if (existCurriculum && existData) {

			final Boolean securityCurriculum = this.curriculumService.security(form.getCurriculumId());
			final Boolean securityData = this.educationDataService.security(form.getId(), form.getCurriculumId());

			if (securityCurriculum && securityData) {
				final Curriculum c = this.curriculumService.findOne(form.getCurriculumId());

				if (binding.hasErrors())
					result = this.createEditModelAndView(form);
				else
					try {
						final EducationData e = this.educationDataService.save(educationReconstruct);
						c.getEducationDatas().add(e);
						this.curriculumService.save(c);
						result = new ModelAndView("redirect:/curriculum/hacker/display.do?curriculumId=" + form.getCurriculumId());
					} catch (final Throwable oops) {
						result = this.createEditModelAndView(form, "curriculum.commit.error");
					}
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}
	// Ancillary methods

	protected ModelAndView createEditModelAndView(final EducationDataForm educationData) {
		ModelAndView result;

		result = this.createEditModelAndView(educationData, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationDataForm educationData, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("curriculum/editEducationData");
		result.addObject("educationData", educationData);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}
}
