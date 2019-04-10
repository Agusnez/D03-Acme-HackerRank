
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private SocialProfileService	socialProfileService;


	@Test
	public void driverListSocialProfiles() {
		final Object testingData[][] = {

			{
				"socialProfile1", "Company1", null
			//1. Todo bien
			}, {
				"socialProfile5", "Company1", IllegalArgumentException.class
			//2. El socialProfile no pertenece a la Company
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListSocialProfiles((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateListSocialProfiles(final String socialProfileId, final String companyUsername, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			super.authenticate(companyUsername);
			final Integer socialProfileIdInteger = super.getEntityId(socialProfileId);
			final Integer companyIdInteger = super.getEntityId(companyUsername);

			final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileIdInteger);

			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findAllByActor(companyIdInteger);
			super.unauthenticate();
			Assert.isTrue(socialProfiles.contains(socialProfile));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	@Test
	public void driverCreateSocialProfile() {
		final Object testingData[][] = {

			{
				"Company1", "nick1", "socialName1", "https://www.youtube.com", null
			//1. Todo bien
			}, {
				null, "nick1", "socialName1", "https://www.youtube.com", IllegalArgumentException.class
			//2. Intenta crearlo alguien no autenticado
			}, {
				"Company1", "", "socialName1", "https://www.youtube.com", ConstraintViolationException.class
			//3. nick = blank
			}, {
				"Company1", "<script>alert('hola')</script>", "socialName1", "https://www.youtube.com", ConstraintViolationException.class
			//4. nick = not safe html
			}, {
				"Company1", "nick1", "", "https://www.youtube.com", ConstraintViolationException.class
			//5. socialName = blank
			}, {
				"Company1", "nick1", "<script>alert('hola')</script>", "https://www.youtube.com", ConstraintViolationException.class
			//6. socialName = not safe html
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSocialProfile((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	protected void templateCreateSocialProfile(final String actor, final String nick, final String socialName, final String link, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			if (actor != null)
				super.authenticate(actor);

			final SocialProfile socialProfile = this.socialProfileService.create();
			socialProfile.setNick(nick);
			socialProfile.setLink(link);
			socialProfile.setSocialName(socialName);

			final SocialProfile saved = this.socialProfileService.save(socialProfile);
			this.socialProfileService.flush();

			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findAll();
			super.unauthenticate();
			Assert.isTrue(socialProfiles.contains(saved));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);

	}

	@Test
	public void driverUpdateSocialProfile() {
		final Object testingData[][] = {

			{
				"Company1", "socialProfile1", "nick1", null
			//1. Todo bien
			}, {
				"Company1", "socialProfile5", "nick1", IllegalArgumentException.class
			//3. El SocialProfile no pertenece al Company autenticado
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateUpdateSocialProfile((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void templateUpdateSocialProfile(final String actor, final String socialProfileId, final String nick, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			super.authenticate(actor);

			final SocialProfile socialProfile = this.socialProfileService.findOne(super.getEntityId(socialProfileId));
			socialProfile.setNick(nick);

			//this.startTransaction();
			final SocialProfile saved = this.socialProfileService.save(socialProfile);
			this.socialProfileService.flush();

			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findAll();
			super.unauthenticate();
			Assert.isTrue(socialProfiles.contains(saved));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
		//this.rollbackTransaction();

	}

	@Test
	public void driverDeleteSocialProfile() {
		final Object testingData[][] = {

			{
				"Company1", "socialProfile1", null
			//1. Todo bien
			}, {
				"Company1", "socialProfile3", IllegalArgumentException.class
			//2. El SocialProfile no pertenece al Company autenticado
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteSocialProfile((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateDeleteSocialProfile(final String actor, final String socialProfileId, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			super.authenticate(actor);

			final SocialProfile socialProfile = this.socialProfileService.findOne(super.getEntityId(socialProfileId));

			this.socialProfileService.delete(socialProfile);
			this.socialProfileService.flush();

			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findAll();
			super.unauthenticate();
			Assert.isTrue(!socialProfiles.contains(socialProfile));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
