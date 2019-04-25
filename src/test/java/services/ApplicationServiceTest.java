
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import forms.ApplicationForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private ApplicationService	applicationService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 9.3: An actor who is authenticated as a company must be able to: List their application by status.
	 * 
	 * b) Negative cases:
	 * 2. The number is incorrect
	 * 
	 * c) Sentence coverage
	 * findAllAcceptedByCompany()=100%
	 * findAllRejectedByCompany()=100%
	 * findAllSubmittedByCompany()=100%
	 * findAllPendingByCompany()=100%
	 * 
	 * d) Data coverage
	 * -Application=0%
	 */

	@Test
	public void driverListApplicationCompany() {
		final Object testingData[][] = {
			{
				"company1", 9, null
			},//1. All fine
			{
				"company1", 0, IllegalArgumentException.class
			},//2. The number is incorrect
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListApplicationCompany((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateListApplicationCompany(final String username, final Integer number, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Integer id = super.getEntityId(username);

			Collection<Application> applicationAccepted;
			Collection<Application> applicationRejected;
			Collection<Application> applicationSubmitted;
			Collection<Application> applicationPending;

			applicationAccepted = this.applicationService.findAllAcceptedByCompany(id);
			applicationRejected = this.applicationService.findAllRejectedByCompany(id);
			applicationSubmitted = this.applicationService.findAllSubmittedByCompany(id);
			applicationPending = this.applicationService.findAllPendingByCompany(id);

			final Collection<Application> applicationAll = new ArrayList<Application>();
			applicationAll.addAll(applicationAccepted);
			applicationAll.addAll(applicationRejected);
			applicationAll.addAll(applicationSubmitted);
			applicationAll.addAll(applicationPending);

			Assert.isTrue(applicationAll.size() == number);

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		super.checkExceptions(expected, caught);
		this.rollbackTransaction();

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 9.3: An actor who is authenticated as a company must be able to: Show their applications.
	 * 
	 * b) Negative cases:
	 * 2.Application not exist
	 * 
	 * c) Sentence coverage
	 * -findOne()=100%
	 * 
	 * d) Data coverage
	 * -Application=0%
	 */

	@Test
	public void driverFindApplicationCompany() {
		final Object testingData[][] = {
			{
				"company1", "application1", null
			},//1. All fine
			{
				"company1", "application1000", AssertionError.class
			},//2. Application not exist
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindApplicationCompany((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateFindApplicationCompany(final String company, final String application, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(company);

			final Application found = this.applicationService.findOne(super.getEntityId(application));
			Assert.notNull(found);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 9.3: An actor who is authenticated as a company must be able to: Accept an application.
	 * 
	 * b) Negative cases:
	 * //2. A company is not registered
	 * 
	 * c) Sentence coverage
	 * -accept()=100%
	 * -findOne()=100%
	 * 
	 * d) Data coverage
	 * -Application=0%
	 */

	@Test
	public void driverAcceptApplication() {
		final Object testingData[][] = {
			{
				"company1", "application1", null
			},//1. All fine
			{
				"hacker1", "application1", IllegalArgumentException.class
			},//2. A company is not registered
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAcceptApplication((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateAcceptApplication(final String username, final String application, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Application res = this.applicationService.accept(super.getEntityId(application));
			Assert.isTrue(res.getStatus() == "ACCEPTED");

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 9.3: An actor who is authenticated as a company must be able to: Reject an application.
	 * 
	 * b) Negative cases:
	 * //2. A company is not registered
	 * 
	 * c) Sentence coverage
	 * -reject()=100%
	 * -findOne()=100%
	 * 
	 * d) Data coverage
	 * -Application=0%
	 */

	@Test
	public void driverRejectApplication() {
		final Object testingData[][] = {
			{
				"company1", "application1", null
			},//1. All fine
			{
				"hacker1", "application1", IllegalArgumentException.class
			},//2. A company is not registered
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRejectApplication((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateRejectApplication(final String username, final String application, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Application res = this.applicationService.reject(super.getEntityId(application));
			Assert.isTrue(res.getStatus() == "REJECTED");

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 10.1: An actor who is authenticated as a hacker must be able to:
	 * List their applications by status
	 * 
	 * b) Negative cases:
	 * 2. The number is incorrect
	 * 
	 * c) Sentence coverage
	 * findAllAcceptedByHacker()=100%
	 * findAllRejectedByHacker()=100%
	 * findAllSubmittedByHacker()=100%
	 * findAllPendingByHacker()=100%
	 * 
	 * d) Data coverage
	 * -Application=0%
	 */

	@Test
	public void driverListApplicationHacker() {
		final Object testingData[][] = {
			{
				"hacker1", 1, null
			},//1. All fine
			{
				"hacker1", 0, IllegalArgumentException.class
			},//2. The number is incorrect
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListApplicationHacker((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateListApplicationHacker(final String username, final Integer number, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			Collection<Application> applicationAccepted;
			Collection<Application> applicationRejected;
			Collection<Application> applicationSubmitted;
			Collection<Application> applicationPending;

			applicationAccepted = this.applicationService.findAllAcceptedByHacker(super.getEntityId(username));
			applicationRejected = this.applicationService.findAllRejectedByHacker(super.getEntityId(username));
			applicationSubmitted = this.applicationService.findAllSubmittedByHacker(super.getEntityId(username));
			applicationPending = this.applicationService.findAllPendingByHacker(super.getEntityId(username));

			final Collection<Application> applicationAll = new ArrayList<Application>();
			applicationAll.addAll(applicationAccepted);
			applicationAll.addAll(applicationRejected);
			applicationAll.addAll(applicationSubmitted);
			applicationAll.addAll(applicationPending);

			Assert.isTrue(applicationAll.size() == number);

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 10.1: An actor who is authenticated as a hacker must be able to:
	 * Show their applications
	 * 
	 * b) Negative cases:
	 * 2.Application not exist
	 * 
	 * c) Sentence coverage
	 * -findOne()=100%
	 * 
	 * d) Data coverage
	 * -Application=0%
	 */

	@Test
	public void driverFindApplicationHacker() {
		final Object testingData[][] = {
			{
				"hacker1", "application1", null
			},//1. All fine
			{
				"hacker1", "application1000", AssertionError.class
			},//2. Application not exist
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindApplicationHacker((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateFindApplicationHacker(final String hacker, final String application, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(hacker);

			final Application found = this.applicationService.findOne(super.getEntityId(application));
			Assert.notNull(found);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 10.1: An actor who is authenticated as a hacker must be able to:
	 * Create an application
	 * 
	 * b) Negative cases:
	 * 2. A hacker is not registered
	 * 
	 * c) Sentence coverage
	 * -create()=100%
	 * 
	 * 
	 * d) Data coverage
	 * -Application=0%
	 */

	@Test
	public void driverCreateApplication() {
		final Object testingData[][] = {
			{
				"hacker1", "answer", null
			},//1. All fine
			{
				"company1", "answer", IllegalArgumentException.class
			},//2. A hacker is not registered
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateApplication((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateCreateApplication(final String hacker, final String application, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(hacker);

			final ApplicationForm app = this.applicationService.create();

			Assert.isTrue(app != null);

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 10.1: An actor who is authenticated as a hacker must be able to:
	 * Update an application.
	 * 
	 * b) Negative cases:
	 * 2. A hacker is not registered
	 * 
	 * c) Sentence coverage
	 * findOne()=100%
	 * save()=100%
	 * 
	 * d) Data coverage
	 * --Application=0%
	 */

	@Test
	public void driverEditApplication() {
		final Object testingData[][] = {
			{
				"hacker1", "answer", null
			},//1. All fine
			{
				"company1", "answer", IllegalArgumentException.class
			},//2. A hacker is not registered
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditApplication((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateEditApplication(final String hacker, final String answer, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(hacker);

			final Application app = this.applicationService.findOne(super.getEntityId("application1"));

			app.setAnswer(answer);

			this.applicationService.save(app);

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage ApplicationService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * ApplicationService = 37,9%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Application = 0%
	 */
}
