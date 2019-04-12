
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.Hacker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private HackerService		hackerService;


	@Test
	public void driverCreateCurriculum() {
		final Object testingData[][] = {
			{
				"hacker1", null
			},//1. All fine
			{
				"company2", IllegalArgumentException.class
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

			final Hacker hacker = this.hackerService.findOne(super.getEntityId(username));

			final Curriculum c = this.curriculumService.create();

			c.setNoCopy(true);
			c.setHacker(hacker);

			this.curriculumService.save(c);
			this.curriculumService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	@Test
	public void driverListCurriculum() {
		final Object testingData[][] = {

			{
				"hacker1", 0, null
			},//1. All fine
			{
				"hacker1", 1651, IllegalArgumentException.class
			},//2. Incorrect result

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListCurriculum((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	protected void templateListCurriculum(final String actor, final Integer expectedInt, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Hacker hacker = this.hackerService.findOne(super.getEntityId(actor));
			final Integer result = this.curriculumService.findAllByHackerId(hacker.getId()).size();
			Assert.isTrue(expectedInt == result);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}
}
