
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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
import domain.Application;
import domain.Company;
import domain.Finder;
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
	private FinderService		finderService;

	@Autowired
	private ApplicationService	applicationService;

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
		result.setFinalMode(false);

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
			for (final Problem p : problemsByPosition) {
				p.getPositions().remove(position);
				this.problemService.save(p);
			}

		this.positionRepository.delete(position);

	}

	public void deleteAll(final int actorId) {

		final Collection<Position> positions = this.findPositionsByCompanyId(actorId);

		if (!positions.isEmpty())
			for (final Position p : positions) {

				final Collection<Finder> finders = this.finderService.findFindersByPositionId(p.getId());
				if (!finders.isEmpty())
					for (final Finder f : finders) {

						f.getPositions().remove(p);
						this.finderService.saveAdmin(f);
					}

				final Collection<Application> apps = this.applicationService.findByPositionId(p.getId());
				if (!apps.isEmpty())
					for (final Application a : apps)
						this.applicationService.delete(a);

				this.positionRepository.delete(p);
			}

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

	public void flush() {

		this.positionRepository.flush();
	}

	public Collection<Position> findPositionsByFilter(String keyword, String companyName) {

		if (keyword == null)
			keyword = "";
		if (companyName == null)
			companyName = "";

		final String formatKey = "%" + keyword + "%";
		final String formatCompany = "%" + companyName + "%";

		final Collection<Position> positions = this.positionRepository.findPositionsByFilter(formatKey, formatCompany);

		return positions;

	}

	public Date convertStringToDate(final String dateString) {
		Date date = null;
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			date = df.parse(dateString);
		} catch (final Exception ex) {
			System.out.println(ex);
		}
		return date;
	}

	public Collection<Position> findPositionsByFinder(final Finder finder) {
		String keyword = finder.getKeyWord();
		String maximumDeadline = finder.getMaximumDeadline();
		Double minimumSalary = finder.getMinimumSalary();
		Double maximumSalary = finder.getMaximumSalary();
		Collection<Position> positions = new HashSet<Position>();

		if (keyword == null)
			keyword = "";
		if (maximumDeadline == null)
			maximumDeadline = "";
		if (minimumSalary == null)
			minimumSalary = 0.;
		if (maximumSalary == null)
			maximumSalary = this.maxOfferedSalaryPosition();

		final String keywordFormat = "%" + keyword + "%";
		final Date maximumDeadlineFormat = this.convertStringToDate(maximumDeadline);

		if (maximumDeadlineFormat != null)
			positions = this.positionRepository.findPositionByFinder(keywordFormat, maximumDeadlineFormat, maximumSalary, minimumSalary);
		else
			positions = this.positionRepository.findPositionByFinderWithoutDeadline(keywordFormat, maximumSalary, minimumSalary);
		return positions;
	}

	public Double maxOfferedSalaryPosition() {

		final Double res = this.positionRepository.maxOfferedSalaryPosition();

		return res;
	}
}
