
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	//The SUT--------------------------------------------------
	@Autowired
	private ConfigurationService	configurationService;


	@Test
	public void EditConfigurationTest() {
		final Object testingData[][] = {
			{
				"admin", "http://example.com", "+34", "2", "40", "example", "example", null
			},//1. All fine
			{
				"admin", "http://example.com", "+34", "-1", "40", "example", "example", ConstraintViolationException.class
			},//2. Finder time < 1
			{
				"admin", "http://example.com", "+34", "-1", "40", "example", "example", ConstraintViolationException.class
			},//3. Finder time > 100

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditConfigurationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], Integer.valueOf((String) testingData[i][3]), Integer.valueOf((String) testingData[i][4]), (String) testingData[i][5],
				(String) testingData[i][6], (Class<?>) testingData[i][7]);
	}

	protected void EditConfigurationTemplate(final String username, final String banner, final String countryCode, final Integer finderTime, final Integer finderResult, final String welcomeMessage, final String welcomeMessageEs, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final Configuration config = this.configurationService.findConfiguration();

			config.setBanner(banner);
			config.setCountryCode(countryCode);
			config.setFinderResult(finderResult);
			config.setFinderTime(finderTime);
			config.setWelcomeMessage(welcomeMessage);
			config.setWelcomeMessageEs(welcomeMessageEs);

			this.configurationService.save(config);
			this.configurationService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		this.checkExceptions(expected, caught);
	}
}
