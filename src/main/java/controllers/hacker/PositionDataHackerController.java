
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

import services.ConfigurationService;
import services.CurriculumService;
import services.PositionDataService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.PositionData;
import forms.PositionDataForm;

@Controller
@RequestMapping("/positionData/hacker")
public class PositionDataHackerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private PositionDataService		positionDataService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		PositionDataForm positionData;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.curriculumService.exist(curriculumId);

		if (exist) {
			final Boolean security = this.curriculumService.security(curriculumId);

			if (security) {
				positionData = new PositionDataForm();

				positionData.setCurriculumId(curriculumId);

				result = this.createEditModelAndView(positionData);
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionRecordId) {
		ModelAndView result;
		final PositionDataForm form;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.positionDataService.exist(positionRecordId);

		if (!(positionRecordId != 0 && exist)) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			form = this.positionDataService.creteForm(positionRecordId);

			final Boolean security = this.positionDataService.security(positionRecordId);

			if (security)
				result = this.createEditModelAndView(form);
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("positionData") final PositionDataForm form, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final PositionData positionReconstruct = this.positionDataService.reconstruct(form, binding);

		final Boolean existCurriculum = this.curriculumService.exist(form.getCurriculumId());

		final Boolean existData = this.positionDataService.exist(form.getId());

		if ((form.getId() == 0 && existCurriculum) || (form.getId() != 0 && existData)) {

			final Boolean securityCurriculum = this.curriculumService.security(form.getCurriculumId());
			final Boolean securityData = this.positionDataService.security(form.getId(), form.getCurriculumId());

			if ((form.getId() == 0 && securityCurriculum) || (form.getId() != 0 && securityData)) {
				final Curriculum c = this.curriculumService.findOne(form.getCurriculumId());

				if (binding.hasErrors())
					result = this.createEditModelAndView(form);
				else
					try {
						if (form.getId() == 0) {
							final PositionData p = this.positionDataService.save(positionReconstruct);
							c.getPositionDatas().add(p);
							this.curriculumService.save(c);

						} else
							this.positionDataService.save(positionReconstruct);

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

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int positionId, @RequestParam final int curriculumId) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist1 = this.positionDataService.exist(positionId);

		final Boolean exist2 = this.curriculumService.exist(curriculumId);

		if (exist1 && exist2) {
			final Boolean security = this.positionDataService.security(positionId, curriculumId);

			final PositionData positionData = this.positionDataService.findOne(positionId);

			if (security)
				try {
					final Curriculum c = this.curriculumService.findOne(curriculumId);
					final Collection<PositionData> datas = c.getPositionDatas();
					datas.remove(positionData);
					this.curriculumService.save(c);

					this.positionDataService.delete(positionData);
					result = new ModelAndView("redirect:/curriculum/hacker/display.do?curriculumId=" + curriculumId);
				} catch (final Throwable oops) {
					result = new ModelAndView("redirect:/curriculum/hacker/list.do");
				}
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final PositionDataForm positionData) {
		ModelAndView result;

		result = this.createEditModelAndView(positionData, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PositionDataForm positionData, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("curriculum/editPositionData");
		result.addObject("positionData", positionData);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}
}
