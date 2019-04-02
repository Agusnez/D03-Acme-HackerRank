
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.PositionService;
import domain.Position;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int positionId) {
		ModelAndView result;

		final Position position = this.positionService.findOne(positionId);
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (position != null) {

			result = new ModelAndView("position/display");
			result.addObject("position", position);
			result.addObject("banner", banner);

		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

}
