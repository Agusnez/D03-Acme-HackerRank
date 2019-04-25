
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Hacker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class HackerServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private HackerService	hackerService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 7.1: An actor who is not authenticated must be able to: Register to the system as a hacker.
	 * 
	 * b) Negative cases:
	 * 2. The email pattern is wrong
	 * 
	 * c) Sentence coverage
	 * -create(): 100%
	 * -save(): 50%
	 * d) Data coverage
	 * -Hacker: 0%
	 */
	@Test
	public void driverRegisterCompany() {
		final Object testingData[][] = {
			{
				"name1", "surnames", 1200000880, "https://google.com", "email1@gmail.com", "672195205", "address1", "company100", "company100", "functionalTest", "VISA", "377964663288126", "12", "2020", "123", null
			},//1. All fine
			{
				"name1", "surnames", 1200000880, "https://google.com", "email1gmail.com", "672195205", "address1", "company100", "company100", "functionalTest", "VISA", "377964663288126", "12", "2020", "123", IllegalArgumentException.class
			},//2. The email pattern is wrong

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterCompany((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14],
				(Class<?>) testingData[i][15]);
	}

	protected void templateRegisterCompany(final String name, final String surnames, final Integer vat, final String photo, final String email, final String phone, final String address, final String username, final String password,
		final String holderName, final String make, final String number, final String expMonth, final String expYear, final String cvv, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Hacker hacker = this.hackerService.create();

			hacker.setName(name);
			hacker.setSurnames(surnames);
			hacker.setVat(vat);

			final CreditCard creditCard = new CreditCard();
			creditCard.setHolderName(holderName);
			creditCard.setCvv(new Integer(cvv));
			creditCard.setExpMonth(new Integer(expMonth));
			creditCard.setExpYear(new Integer(expYear));
			creditCard.setMake(make);
			creditCard.setNumber(number);

			hacker.setCreditCard(creditCard);
			hacker.setPhoto(photo);
			hacker.setEmail(email);
			hacker.setPhone(phone);
			hacker.setAddress(address);

			hacker.getUserAccount().setUsername(username);
			hacker.getUserAccount().setPassword(password);

			this.startTransaction();

			this.hackerService.save(hacker);
			this.hackerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 8.2: An actor who is authenticated must be able to: Edit his/her personal data.
	 * 
	 * b) Negative cases:
	 * 2. The expiration year of the credit card is past
	 * 
	 * c) Sentence coverage
	 * -save():49,1%
	 * d) Data coverage
	 * -Hacker: 0%
	 */
	@Test
	public void driverEditCompany() {
		final Object testingData[][] = {
			{
				"name1", "surnames", 12456780, "https://google.com", "email1@gmail.com", "672195205", "address1", "hacker1", "functionalTest", "VISA", "377964663288126", "12", "2020", "123", "hacker1", null
			},//1. All fine
			{
				"name1", "surnames", 12456780, "https://google.com", "email1gmail.com", "672195205", "address1", "hacker1", "functionalTest", "VISA", "377964663288126", "12", "2020", "123", "hacker1", ConstraintViolationException.class
			},//2. The email pattern is wrong

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditCompany((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14],
				(Class<?>) testingData[i][15]);
	}
	protected void templateEditCompany(final String name, final String surnames, final Integer vat, final String photo, final String email, final String phone, final String address, final String username, final String holderName, final String make,
		final String number, final String expMonth, final String expYear, final String cvv, final String hackerToEdit, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);
			final Hacker hacker = this.hackerService.findOne(super.getEntityId(hackerToEdit));

			hacker.setName(name);
			hacker.setSurnames(surnames);
			hacker.setVat(vat);

			final CreditCard creditCard = hacker.getCreditCard();

			creditCard.setCvv(new Integer(cvv));
			creditCard.setExpMonth(new Integer(expMonth));
			creditCard.setExpYear(new Integer(expYear));
			creditCard.setHolderName(holderName);
			creditCard.setMake(make);
			creditCard.setNumber(number);

			hacker.setPhoto(photo);
			hacker.setEmail(email);
			hacker.setPhone(phone);
			hacker.setAddress(address);

			this.hackerService.save(hacker);
			this.hackerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage HackerService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * HackerService = 60,7%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Hacker = 0%
	 */

}
