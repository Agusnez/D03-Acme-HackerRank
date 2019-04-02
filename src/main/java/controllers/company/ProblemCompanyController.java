
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.ConfigurationService;
import services.ProblemService;
import controllers.AbstractController;
import domain.Company;
import domain.Problem;

@Controller
@RequestMapping("/problem/company")
public class ProblemCompanyController extends AbstractController {

	@Autowired
	private ProblemService			problemService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;


	//List---------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<Problem> problems;
		final Company comp;

		comp = this.companyService.findByPrincipal();

		problems = this.problemService.findProblemByCompanyId(comp.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("problem/list");
		result.addObject("problems", problems);
		result.addObject("requestURI", "problem/company/list.do");
		result.addObject("pagesize", 5);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		result.addObject("autoridad", "company");

		return result;

	}
	//Editar-------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int problemId) {
		ModelAndView result;
		Boolean security;

		final Problem problemFind = this.problemService.findOne(problemId);
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (problemFind == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else if (problemFind.getFinalMode() == true) {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("banner", banner);
		} else {
			security = this.problemService.problemCompanySecurity(problemId);

			if (security)
				result = this.createEditModelAndView(problemFind, null);
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute(value = "problem") Problem problem, final BindingResult binding) {
		ModelAndView result;
		Boolean security;

		final String banner = this.configurationService.findConfiguration().getBanner();
		security = this.problemService.problemCompanySecurity(problem.getId());

		if (problem.getFinalMode() == true || security == false) {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("banner", banner);
		} else {

			problem = this.problemService.reconstruct(problem, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(problem, null);
			else
				try {
					this.problemService.save(problem);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(problem, "problem.commit.error");

				}
		}

		return result;
	}

	//Delete--------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Problem problem, final BindingResult binding) {
		ModelAndView result;

		problem = this.problemService.findOne(problem.getId());
		final Company company = this.companyService.findByPrincipal();

		if (problem.getCompany().getId() == company.getId() && problem.getFinalMode() == false)
			try {
				this.problemService.delete(problem);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(problem, "problem.commit.error");
			}
		else
			result = new ModelAndView("redirect:/welcome/index.do");
		return result;
	}

	//Display------------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int problemId) {
		ModelAndView result;
		Boolean security;

		final Problem problemFind = this.problemService.findOne(problemId);
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (problemFind == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {
			security = this.problemService.problemCompanySecurity(problemId);

			if (security) {
				result = new ModelAndView("problem/display");
				result.addObject("problem", problemFind);
				result.addObject("banner", banner);
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	//Ancillary methods---------------------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Problem problem, final String messageCode) {
		final ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("problem/edit");
		result.addObject("problem", problem);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());

		return result;
	}

}
