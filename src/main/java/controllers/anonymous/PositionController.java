
package controllers.anonymous;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.ConfigurationService;
import services.PositionService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import forms.FilterForm;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	@Autowired
	private PositionService			positionService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/listByCompany", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int companyId) {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		final Company notFound = this.companyService.findOne(companyId);

		if (notFound == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			final Collection<Position> positions;

			positions = this.positionService.findPositionsByCompanyIdAndFinalModeTrue(companyId);

			result = new ModelAndView("position/list");
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/listByCompany.do");
			result.addObject("pagesize", 5);
			result.addObject("banner", banner);
			result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
			result.addObject("autoridad", "");

			final FilterForm filterForm = new FilterForm();
			result.addObject("filterForm", filterForm);

		}
		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();
		final Collection<Position> positions;

		positions = this.positionService.findPositionsFinalModeTrue();

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/list.do");
		result.addObject("pagesize", 5);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		result.addObject("autoridad", "");

		final FilterForm filterForm = new FilterForm();
		result.addObject("filterForm", filterForm);

		return result;

	}

	@RequestMapping(value = "/listByFilter", method = RequestMethod.POST)
	public ModelAndView listByFilter(final String keyword, final String companyName) {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		final Collection<Position> positions = this.positionService.findPositionsByFilter(keyword, companyName);

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		result.addObject("autoridad", "");

		final FilterForm filterForm = new FilterForm();
		result.addObject("filterForm", filterForm);

		return result;
	}
}
