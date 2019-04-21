
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
import java.util.Arrays;

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

		final Double avgPC = this.administratorService.avgOfPositionsPerCompany();
		final Integer minPC = this.administratorService.minOfPositionsPerCompany();
		final Integer maxPC = this.administratorService.maxOfPositionsPerCompany();
		final Double stdPC = this.administratorService.stdOfPositionsPerCompany();
		
		final Double avgAH = this.administratorService.avgOfApplicationsPerHacker();
		final Integer minAH = this.administratorService.minOfApplicationsPerHacker();
		final Integer maxAH = this.administratorService.maxOfApplicationsPerHacker();
		final Double stdAH = this.administratorService.stdOfApplicationsPerHacker();
		
		final List<String> topC = this.administratorService.topCompaniesWithMorePositions();
		
		final List<String> topH = this.administratorService.topHackersWithMoreApplications();
		
		
		final Double avgS = this.administratorService.avgSalaries();
		final Integer minS = this.administratorService.minSalary();
		final Integer maxS = this.administratorService.maxSalary();
		final Double stdS = this.administratorService.stdSalaries();
		
		final Position bP = this.administratorService.findBestPosition();
		final Position wP = this.administratorService.findWorstPosition();
		
		final Integer minCH = this.administratorService.minNumberOfCurriculaPerHacker();
		final Integer maxCH = this.administratorService.maxNumberOfCurriculaPerHacker();
		final Double avgCH = this.administratorService.avgNumberOfCurriculaPerHacker();
		final Double stdCH = this.administratorService.stdNumberOfCurriculaPerHacker();
		
		final Double stdRF = this.administratorService.stdNumberOfResultsInFinders();
		final Integer minRF = this.administratorService.minNumberOfResultsInFinders();
		final Integer maxRF = this.administratorService.maxNumberOfResultsInFinders();
		final Double avgRF = this.administratorService.avgNumberOfResultsInFinders();
		
		final Double ratioEF = this.administratorService.ratioEmptyNotEmptyFinders();
		

		result = new ModelAndView("administrator/dashboard");
		result.addObject("avgPC",avgPC);
		result.addObject("minPC",minPC);
		result.addObject("maxPC",maxPC);
		result.addObject("stdPC",stdPC);
		result.addObject("avgAH",avgAH);
		result.addObject("minAH",minAH);
		result.addObject("maxAH",maxAH);
		result.addObject("stdAH",stdAH);
		result.addObject("topC", topC);
		result.addObject("topH", topH);
		result.addObject("avgS", avgS);
		result.addObject("stdS", stdS);
		result.addObject("minS", minS);
		result.addObject("maxS", maxS);
		result.addObject("bP", bP);
		result.addObject("wP", wP);
		result.addObject("minCH", minCH);
		result.addObject("maxCH", maxCH);
		result.addObject("avgCH", avgCH);
		result.addObject("stdCH", stdCH);
		result.addObject("avgRF", avgRF);
		result.addObject("minRF", minRF);
		result.addObject("maxRF", maxRF);
		result.addObject("stdRF", stdRF);
		result.addObject("ratioEF", ratioEF);
		result.addObject("banner", banner);

		return result;
	}
}
