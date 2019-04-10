
package controllers.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.ApplicationService;
import services.CompanyService;
import services.ConfigurationService;
import services.HackerService;

@Controller
@RequestMapping("/application/company")
public class ApplicationCompanyController {

	// Services ---------------------------------------------------

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;

}
