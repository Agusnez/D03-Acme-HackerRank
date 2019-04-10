
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	@Test
	public void driverExchangeMessage() {
		final Object testingData[][] = {
			{
				"Company1", "Hacker1", "Body1", "Subject1", "Tag1", null
			},
			//1.All right
			{
				"Company1", "Hacker1", "", "Subject1", "Tag1", ConstraintViolationException.class
			},//2.Body blank
			{
				"Company1", "Hacker1", "Body1", "", "Tag1", ConstraintViolationException.class
			},//3.Subject blank

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateExchangeMessage((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void templateExchangeMessage(final String sender, final String recipient, final String body, final String subject, final String tags, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			if (sender != null)
				super.authenticate(sender);

			final Message message = this.messageService.create3();
			message.setBody(body);
			message.setRecipient(this.actorService.findOne(super.getEntityId(recipient)));
			message.setSubject(subject);
			message.setTags(tags);

			this.messageService.save(message);
			this.messageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();

	}

}
