
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.EducationData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EducationDataServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private EducationDataService	educationDataService;


	@Test
	public void driverCreateEducationData() {
		final Object testingData[][] = {
			{
				"hacker1", "test", "test", 5.0, "1998/06/29", "2000/06/29", null
			},//1. All fine
			{
				"hacker1", null, "test", 5.0, "1998/06/29", "2000/06/29", ConstraintViolationException.class
			},//2. Degree = null
			{
				"hacker1", "		", "test", 5.0, "1998/06/29", "2000/06/29", ConstraintViolationException.class
			},//3. Degree = blank
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateEducationData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], this.convertStringToDate((String) testingData[i][4]),
				this.convertStringToDate((String) testingData[i][5]), (Class<?>) testingData[i][6]);
	}
	protected void templateCreateEducationData(final String username, final String degree, final String institution, final Double mark, final Date startDate, final Date endDate, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final EducationData data = this.educationDataService.create();

			data.setDegree(degree);
			data.setInstitution(institution);
			data.setMark(mark);
			data.setStartDate(startDate);
			data.setEndDate(endDate);

			this.educationDataService.save(data);
			this.educationDataService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	protected Date convertStringToDate(final String dateString) {
		Date date = null;

		if (dateString != null) {
			final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			try {
				date = df.parse(dateString);
			} catch (final Exception ex) {
				System.out.println(ex);
			}
		}

		return date;
	}
}
