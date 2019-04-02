
package services;

import java.util.Collection;
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

	@Autowired
	private ProblemService		problemService;


	//Simple CRUD methods --------------------------------------------------

	public Position create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		final Position result = new Position();

		result.setCompany(this.companyService.findByPrincipal());
		result.setTicker(this.generateTicker());

		return result;

	}

	public Collection<Position> findAll() {

		final Collection<Position> positions = this.positionRepository.findAll();

		Assert.notNull(positions);

		return positions;
	}

	public Position findOne(final int positionId) {

		final Position position = this.positionRepository.findOne(positionId);

		return position;

	}

	public Position save(final Position position) {

		Assert.notNull(position);
		Position result;

		if (position.getFinalMode())
			//COMPROBAR QUE TENGA AL MENOS 2 PROBLEMS ASIGNADOS, A LA ESPERA METODO SANTI
			if (position.getId() != 0) {
				final Position positionBBDD = this.findOne(position.getId());
				Assert.isTrue(!positionBBDD.getFinalMode());
			}

		result = this.positionRepository.save(position);
		return result;

	}

	public void delete(final Position position) {

		Assert.notNull(position);
		Assert.isTrue(position.getId() != 0);
		Assert.isTrue(!position.getFinalMode());

		//COMPROBAR QUE TENGA AL MENOS 2 PROBLEMS ASIGNADOS, A LA ESPERA METODO SANTI

		this.delete(position);

	}

	//Other bussines methods--------------------------------

	private String generateTicker() {

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
			this.generateTicker();

		return ticker;

	}

}
