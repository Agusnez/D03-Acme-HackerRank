
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/listSpammers", method = RequestMethod.GET)
	public ModelAndView listSpammers() {
		final ModelAndView result;
		Collection<Actor> spammers;

		final String banner = this.configurationService.findConfiguration().getBanner();

		spammers = this.actorService.actorSpammer();

		result = new ModelAndView("actor/listSpammers");
		result.addObject("spammers", spammers);
		result.addObject("banner", banner);
		result.addObject("requestURI", "actor/administrator/listSpammers.do");

		return result;
	}

}
