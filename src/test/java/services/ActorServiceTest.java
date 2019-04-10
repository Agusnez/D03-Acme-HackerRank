
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
public class ActorServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private ActorService	actorService;


	@Test
	public void driverBanActor() {

		final Object testingData[][] = {

			{
				"Company1", null
			}, //1. All fine
			{
				"administrator1", AssertionError.class
			}, //2. Ban yourself

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateBanActor((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateBanActor(final String actorBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			this.startTransaction();

			super.authenticate("admin");

			final Actor actor = this.actorService.findOne(super.getEntityId(actorBean));

			this.actorService.banOrUnBanActor(actor);
			this.actorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * ACME-MADRUGÁ
	 * a)(Level A) Requirement 28.6: Administrator can unban an actor
	 * 
	 * b) Negative cases:
	 * 2. Unban yourself
	 * 
	 * c) Sentence coverage
	 * -banOrUnBanActor(): 2 passed cases / 6 total cases = 33,33333%
	 * 
	 * d) Data coverage
	 * -Segment: 0 passed cases / 4 total cases = 0%
	 */

	@Test
	public void driverUnbanActor() {

		final Object testingData[][] = {

			{
				"Hacker1", null
			}, //1. All fine
			{
				"administrator1", AssertionError.class
			}, //2. Unban yourself

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateUnbanActor((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateUnbanActor(final String actorBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			this.startTransaction();

			super.authenticate("admin");

			final Actor actor = this.actorService.findOne(super.getEntityId(actorBean));

			this.actorService.banOrUnBanActor(actor);
			this.actorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}
}
