
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
import domain.Company;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CompanyServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private CompanyService	companyService;


	/*
	 * ----CALCULATE SENTENCE COVERAGE----
	 */

	/*
	 * ----CALCULATE DATA COVERAGE----
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 7.1: An actor who is not authenticated must be able to: Register to the system as a company.
	 * 
	 * b) Negative cases:
	 * 2. The email pattern is wrong
	 * 
	 * c) Sentence coverage
	 * -create(): 100%
	 * -save():50%
	 * d) Data coverage
	 * -Company: 0%
	 */
	@Test
	public void driverRegisterCompany() {
		final Object testingData[][] = {
			{
				"endesa", "name1", "surnames", 12.0, "https://google.com", "email1@gmail.com", "672195205", "address1", "company100", "company100", "functionalTest", "VISA", "377964663288126", "12", "2020", "123", null
			},//1. All fine
			{
				"endesa", "name1", "surnames", 12.0, "https://google.com", "email1gmail.com", "672195205", "address1", "company100", "company100", "functionalTest", "VISA", "377964663288126", "12", "2020", "123", IllegalArgumentException.class
			},//2. The email pattern is wrong

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterCompany((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14],
				(String) testingData[i][15], (Class<?>) testingData[i][16]);
	}

	protected void templateRegisterCompany(final String commercialName, final String name, final String surnames, final Double vat, final String photo, final String email, final String phone, final String address, final String username,
		final String password, final String holderName, final String make, final String number, final String expMonth, final String expYear, final String cvv, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Company company = this.companyService.create();

			company.setCommercialName(commercialName);
			company.setName(name);
			company.setSurnames(surnames);
			company.setVat(vat);

			final CreditCard creditCard = new CreditCard();
			creditCard.setHolderName(holderName);
			creditCard.setCvv(new Integer(cvv));
			creditCard.setExpMonth(new Integer(expMonth));
			creditCard.setExpYear(new Integer(expYear));
			creditCard.setMake(make);
			creditCard.setNumber(number);

			company.setCreditCard(creditCard);
			company.setPhoto(photo);
			company.setEmail(email);
			company.setPhone(phone);
			company.setAddress(address);

			company.getUserAccount().setUsername(username);
			company.getUserAccount().setPassword(password);

			this.startTransaction();

			this.companyService.save(company);
			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();

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
	 * -Company: 0%
	 */
	@Test
	public void driverEditCompany() {
		final Object testingData[][] = {
			{
				"endesa", "name1", "surnames", 12.0, "https://google.com", "email1@gmail.com", "672195205", "address1", "company1", "functionalTest", "VISA", "377964663288126", "12", "2020", "123", "Company1", null
			},//1. All fine
			{
				"endesa", "name1", "surnames", 12.0, "https://google.com", "email1gmail.com", "672195205", "address1", "company1", "functionalTest", "VISA", "377964663288126", "12", "2018", "123", "Company1", ConstraintViolationException.class
			},//2. The expiration year of the credit card is past

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditCompany((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14],
				(String) testingData[i][15], (Class<?>) testingData[i][16]);
	}

	protected void templateEditCompany(final String commercialName, final String name, final String surnames, final Double vat, final String photo, final String email, final String phone, final String address, final String username,
		final String holderName, final String make, final String number, final String expMonth, final String expYear, final String cvv, final String companyToEdit, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);
			final Company company = this.companyService.findOne(super.getEntityId(companyToEdit));

			company.setCommercialName(commercialName);
			company.setName(name);
			company.setSurnames(surnames);
			company.setVat(vat);

			final CreditCard creditCard = company.getCreditCard();

			creditCard.setCvv(new Integer(cvv));
			creditCard.setExpMonth(new Integer(expMonth));
			creditCard.setExpYear(new Integer(expYear));
			creditCard.setHolderName(holderName);
			creditCard.setMake(make);
			creditCard.setNumber(number);

			company.setPhoto(photo);
			company.setEmail(email);
			company.setPhone(phone);
			company.setAddress(address);

			this.companyService.save(company);
			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		super.checkExceptions(expected, caught);
		this.rollbackTransaction();

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 7.3: An actor who is not authenticated as a company must be able to: List the comapanies available and navigate to the corresponding positions
	 * Requirement 8.1: An actor who is authenticated must be able to: Do the same as an actor who is not authenticated, but register to the system.
	 * 
	 * b) Negative cases:
	 * 3. Company lists the positions available by a company, but the number of positions is incorrect
	 * 
	 * c) Sentence coverage
	 * -findAll(): 100%
	 * d) Data coverage
	 * -Company: 0%
	 */
	@Test
	public void driverListCompaniesAvailable() {
		final Object testingData[][] = {
			{
				"company1", 10, null
			},//1. Company lists the companies available (All fine)
			{
				null, 10, null
			},//2. Not registered actor lists the companies available (All fine) 
			{
				"company1", 4, IllegalArgumentException.class
			},//3. Company lists the companies available, but the number of companies is incorrect
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListCompaniesAvailable((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateListCompaniesAvailable(final String username, final Integer number, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			if (username != null)
				this.authenticate(username);

			final Collection<Company> companies = this.companyService.findAll();

			Assert.isTrue(companies.size() == number);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		super.checkExceptions(expected, caught);
		this.rollbackTransaction();
	}

}
