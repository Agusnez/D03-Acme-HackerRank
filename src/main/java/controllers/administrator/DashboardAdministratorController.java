
package controllers.administrator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Position;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	//Services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Methods

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Double avg = this.administratorService.avgOfPositionsPerCompany();
		final Position p = this.administratorService.findBestPosition();
		final List<String> topC = this.administratorService.topCompaniesWithMorePositions();

		result = new ModelAndView("administrator/dashboard");
		result.addObject("companies", topC);
		result.addObject("banner", banner);

		return result;
	}
}
