
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Curriculum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private CurriculumService	curriculumService;


	@Test
	public void driverCreateCurriculum() {
		final Object testingData[][] = {
			{
				"Hacker1", null
			},//1. All fine
			{
				"Company2", IllegalArgumentException.class
			},//2. Invalid authority
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateHistory((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void templateCreateHistory(final String username, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final Curriculum c = this.curriculumService.create();

			c.setNoCopy(true);

			this.curriculumService.save(c);
			this.curriculumService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		super.checkExceptions(expected, caught);

		this.rollbackTransaction();

	}
}
