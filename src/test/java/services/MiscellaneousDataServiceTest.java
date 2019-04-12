
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.MiscellaneousData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MiscellaneousDataServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;


	@Test
	public void driverCreateMiscellanousData() {
		final Object testingData[][] = {
			{
				"hacker1", "test", "http://test.com", null
			},//1. All fine
			{
				"hacker1", null, "http://test.com", ConstraintViolationException.class
			},//2. Text = null
			{
				"hacker1", "		", "http://test.com", ConstraintViolationException.class
			},//3. Text = blank
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateMiscellanousData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void templateCreateMiscellanousData(final String username, final String text, final String attachment, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final MiscellaneousData data = this.miscellaneousDataService.create();
			data.setText(text);
			final Collection<String> attachments = new HashSet<>();
			attachments.add(attachment);
			data.setAttachments(attachments);

			this.miscellaneousDataService.save(data);
			this.miscellaneousDataService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}
}