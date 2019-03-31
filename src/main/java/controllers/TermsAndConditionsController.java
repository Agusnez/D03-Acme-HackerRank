
package controllers;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/termsAndConditions")
public class TermsAndConditionsController extends AbstractController {

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView termsAndConditions() {
		ModelAndView result;

		final String language = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("misc/termsAndConditions");
		result.addObject("language", language);

		return result;
	}

}
