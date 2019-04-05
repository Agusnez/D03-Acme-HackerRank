
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Administrator;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//The SUT--------------------------------------------------
	@Autowired
	private AdministratorService	adminService;


	@Test
	public void driverRegisterAdmin() {
		final Object testingData[][] = {
			{
				"admin", "name1", "surname1", "121.00", "https://google.com", "email1@gmail.com", "cvycjwbi", "visa", "1111222233334444", "12", "2020", "900", "672195205", "address1", "admin55", "admin55", null
			},//1. All fine

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterAdmin((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], Double.valueOf((String) testingData[i][3]), (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], Integer.valueOf((String) testingData[i][9]), Integer.valueOf((String) testingData[i][10]), Integer.valueOf((String) testingData[i][11]), (String) testingData[i][12],
				(String) testingData[i][13], (String) testingData[i][14], (String) testingData[i][15], (Class<?>) testingData[i][16]);
	}
	protected void templateRegisterAdmin(final String usernameLogin, final String name, final String surnames, final Double vat, final String photo, final String email, final String holderName, final String make, final String number,
		final Integer expMonth, final Integer expYear, final Integer cvv, final String phone, final String address, final String username, final String password, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			super.authenticate(usernameLogin);

			final CreditCard cc = new CreditCard();
			cc.setHolderName(holderName);
			cc.setMake(make);
			cc.setNumber(number);
			cc.setExpMonth(expMonth);
			cc.setExpYear(expYear);
			cc.setCvv(cvv);

			final Administrator admin = this.adminService.create();

			admin.setName(name);
			admin.setSurnames(surnames);
			admin.setVat(vat);
			admin.setPhoto(photo);
			admin.setEmail(email);
			admin.setCreditCard(cc);
			admin.setPhone(phone);
			admin.setAddress(address);

			admin.getUserAccount().setUsername(username);
			admin.getUserAccount().setPassword(password);

			this.startTransaction();

			this.adminService.save(admin);
			this.adminService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}
}
