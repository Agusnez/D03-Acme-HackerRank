
package services;

import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import security.Authority;
import domain.Actor;
import domain.Position;

@Service
@Transactional
public class PositionService {

	//Managed Repository ---------------------------------------------------
	@Autowired
	private PositionRepository	positionRepository;

	//Supporting services --------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CompanyService		companyService;


	//Simple CRUD methods --------------------------------------------------

	public Position create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		final Position result = new Position();

		result.setCompany(this.companyService.findByPrincipal());

		return result;

	}

	private String generateTicker(final Date moment) {

		final String companyName = this.companyService.findByPrincipal().getCommercialName();

		final int size = companyName.length();

		String firstLetters = companyName.substring(0, size);

		while (firstLetters.length() < 4)
			firstLetters += "X";

		final String alphaNumeric = "1234567890";
		final StringBuilder salt = new StringBuilder();
		final Random rnd = new Random();
		while (salt.length() < 4) { // length of the random string.
			final int index = (int) (rnd.nextFloat() * alphaNumeric.length());
			salt.append(alphaNumeric.charAt(index));
		}
		final String randomAlphaNumeric = salt.toString();

		final String ticker = firstLetters + "-" + randomAlphaNumeric;

		final int positionSameTicker = this.positionRepository.countParadeWithTicker(ticker);

		//nos aseguramos que que sea �nico
		while (positionSameTicker > 0)
			this.generateTicker(moment);

		return ticker;

	}
	//Other bussines methods--------------------------------

}
