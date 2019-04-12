
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.PersonalData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PersonalDataServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private PersonalDataService	personalDataService;


	@Test
	public void driverCreateEducationData() {
		final Object testingData[][] = {
			{
				"hacker1", "test", "test", "954920633", "http://github.com", "http://linkedIn.com", null
			},//1. All fine
			{
				"hacker1", "test", null, "954920633", "http://github.com", "http://linkedIn.com", ConstraintViolationException.class
			},//2. Degree = null
			{
				"hacker1", "test", "		", "954920633", "http://github.com", "http://linkedIn.com", ConstraintViolationException.class
			},//3. Degree = blank
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreatePersonalData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void templateCreatePersonalData(final String username, final String fullName, final String statement, final String phone, final String linkGitHubProfile, final String linkLinkedInProfile, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final PersonalData data = this.personalDataService.create();

			data.setFullName(fullName);
			data.setStatement(statement);
			data.setPhone(phone);
			data.setLinkGitHubProfile(linkGitHubProfile);
			data.setLinkLinkedInProfile(linkLinkedInProfile);

			this.personalDataService.save(data);
			this.personalDataService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}
}