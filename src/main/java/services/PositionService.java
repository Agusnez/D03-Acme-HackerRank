
package services;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import security.Authority;
import domain.Actor;
import domain.Company;
import domain.Position;
import domain.Problem;

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

	@Autowired
	private Validator			validator;


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

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		Position result;

		if (position.getId() != 0) {
			//Seguridad position con su propietario
			Assert.isTrue(this.positionCompanySecurity(position.getId()));
			final Position positionBBDD = this.findOne(position.getId());
			Assert.isTrue(!positionBBDD.getFinalMode());
		}

		if (position.getFinalMode()) {
			final Collection<Problem> problemsByPosition = this.problemService.findProblemsByPositionId(position.getId());
			Assert.isTrue(problemsByPosition.size() >= 2);
		}
		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		Assert.isTrue(position.getDeadline().after(currentMoment));
		result = this.positionRepository.save(position);
		return result;

	}
	public void delete(final Position position) {

		Assert.notNull(position);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		Assert.isTrue(position.getId() != 0);

		//Seguridad position con su propietario
		Assert.isTrue(this.positionCompanySecurity(position.getId()));

		Assert.isTrue(!position.getFinalMode());

		//Eliminamos dependencias con problems
		final Collection<Problem> problemsByPosition = this.problemService.findProblemsByPositionId(position.getId());
		if (problemsByPosition != null && problemsByPosition.size() > 0)
			for (final Problem p : problemsByPosition)
				p.setPosition(null);

		this.positionRepository.delete(position);

	}

	//Other bussines methods--------------------------------

	private String generateTicker() {

		final String companyName = this.companyService.findByPrincipal().getCommercialName();

		String firstLetters = companyName.substring(0, 4);

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

		final int positionSameTicker = this.positionRepository.countPositionWithTicker(ticker);

		//nos aseguramos que que sea �nico
		while (positionSameTicker > 0)
			this.generateTicker();

		return ticker;

	}

	public Collection<Position> findPositionsByCompanyId(final int companyId) {

		final Collection<Position> positions = this.positionRepository.findPositionsByCompanyId(companyId);

		return positions;
	}

	public Collection<Position> findPositionsByCompanyIdAndFinalModeTrue(final int companyId) {

		final Collection<Position> positions = this.positionRepository.findPositionsByCompanyIdAndFinalModeTrue(companyId);

		return positions;
	}

	public Collection<Position> findPositionsFinalModeTrue() {

		final Collection<Position> positions = this.positionRepository.findPositionsFinalModeTrue();

		return positions;
	}

	public Boolean positionCompanySecurity(final int positionId) {
		Boolean res = false;
		Assert.notNull(positionId);

		final Company companyNow = this.companyService.findByPrincipal();

		final Position position = this.findOne(positionId);

		final Company owner = position.getCompany();

		if (companyNow.getId() == owner.getId())
			res = true;

		return res;
	}

	public Position reconstruct(final Position position, final BindingResult binding) {

		Position result = position;
		Assert.notNull(position);
		final Position positionNew = this.create();

		if (position.getId() == 0) {

			position.setCompany(positionNew.getCompany());
			position.setTicker(positionNew.getTicker());

			this.validator.validate(position, binding);

			result = position;
		} else {

			final Position positionBBDD = this.findOne(position.getId());

			position.setCompany(positionBBDD.getCompany());
			position.setTicker(positionBBDD.getTicker());

			this.validator.validate(position, binding);

		}

		return result;

	}

}
