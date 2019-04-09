
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.CompanyService;
import services.ConfigurationService;
import services.HackerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Hacker;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services-----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Actor actor = this.actorService.findByPrincipal();

		Collection<Hacker> hackers;
		Collection<Company> companies;
		Collection<Administrator> administrators;

		hackers = this.hackerService.findAll();
		companies = this.companyService.findAll();
		administrators = this.administratorService.findAll();

		if (hackers.contains(actor))
			hackers.remove(actor);
		else if (companies.contains(actor))
			companies.remove(actor);
		else if (administrators.contains(actor))
			administrators.remove(actor);

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/list");
		result.addObject("hackers", hackers);
		result.addObject("companies", companies);
		result.addObject("administrators", administrators);
		result.addObject("banner", banner);

		result.addObject("requestURI", "actor/list.do");

		return result;

	}

}
