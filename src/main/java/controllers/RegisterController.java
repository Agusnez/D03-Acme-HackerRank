
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Credentials;
import services.CompanyService;
import services.ConfigurationService;
import services.HackerService;
import domain.Company;
import domain.Hacker;
import forms.RegisterCompanyForm;
import forms.RegisterHackerForm;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	// Services

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private ConfigurationService	configurationService;


	//Registrar Company
	@RequestMapping(value = "/createCompany", method = RequestMethod.GET)
	public ModelAndView createCompany() {
		final ModelAndView result;
		final RegisterCompanyForm company = new RegisterCompanyForm();

		result = this.createEditModelAndViewCompany(company);

		return result;
	}

	@RequestMapping(value = "/saveCompany", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCompany(@ModelAttribute("company") final RegisterCompanyForm form, final BindingResult binding) {
		ModelAndView result;
		final Company company;

		company = this.companyService.reconstruct(form, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewCompany(form);
		else
			try {
				Assert.isTrue(form.getCheckbox());
				Assert.isTrue(form.checkPassword());
				this.companyService.save(company);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(company.getUserAccount().getUsername());
				credentials.setPassword(company.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/security/login.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCompany(form, "company.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndViewCompany(final RegisterCompanyForm company) {
		ModelAndView result;

		result = this.createEditModelAndViewCompany(company, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewCompany(final RegisterCompanyForm company, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("security/signUpCompany");
		result.addObject("company", company);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

	//Registrar Hacker

	@RequestMapping(value = "/createHacker", method = RequestMethod.GET)
	public ModelAndView createHacker() {
		final ModelAndView result;
		final RegisterHackerForm hacker = new RegisterHackerForm();

		result = this.createEditModelAndViewHacker(hacker);

		return result;
	}

	@RequestMapping(value = "/saveHacker", method = RequestMethod.POST, params = "save")
	public ModelAndView saveHacker(@ModelAttribute("hacker") final RegisterHackerForm form, final BindingResult binding) {
		ModelAndView result;
		final Hacker hacker;

		hacker = this.hackerService.reconstruct(form, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewHacker(form);
		else
			try {
				Assert.isTrue(form.getCheckbox());
				Assert.isTrue(form.checkPassword());
				this.hackerService.save(hacker);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(hacker.getUserAccount().getUsername());
				credentials.setPassword(hacker.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/security/login.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewHacker(form, "hacker.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndViewHacker(final RegisterHackerForm hacker) {
		ModelAndView result;

		result = this.createEditModelAndViewHacker(hacker, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewHacker(final RegisterHackerForm hacker, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("security/signUpHacker");
		result.addObject("hacker", hacker);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

}
