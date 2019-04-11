
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.CurriculumRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.Hacker;
import forms.CreateCurriculumForm;

@Service
@Transactional
public class CurriculumService {

	// Managed Repository ------------------------
	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private HackerService			hackerService;


	// Simple CRUD methods -----------------------

	public Curriculum create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HACKER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Curriculum result;
		result = new Curriculum();

		return result;
	}

	public Collection<Curriculum> findAll() {

		Collection<Curriculum> result;

		result = this.curriculumRepository.findAll();

		return result;
	}

	public Curriculum findOne(final int curriculumId) {

		Assert.notNull(curriculumId);
		Curriculum result;
		result = this.curriculumRepository.findOne(curriculumId);
		return result;
	}

	public Curriculum save(final Curriculum curriculum) {

		Assert.notNull(curriculum);

		Curriculum result;

		result = this.curriculumRepository.save(curriculum);

		return result;

	}

	public void delete(final Curriculum curriculum) {

		Assert.notNull(curriculum);

		this.curriculumRepository.delete(curriculum);

	}

	public void deleteAll(final int actorId) {

		final Collection<Curriculum> curricula = this.findAllByHackerId(actorId);

		if (!curricula.isEmpty())
			for (final Curriculum c : curricula)
				this.curriculumRepository.delete(c);
	}

	public Curriculum reconstruct(final CreateCurriculumForm form, final BindingResult binding) {

		final Hacker actor = this.hackerService.findByPrincipal();

		final Curriculum result = this.create();

		result.setNoCopy(true);
		result.setHacker(actor);

		return result;

	}

	//Other methods

	public Collection<Curriculum> findByHackerId(final int hackerId) {

		final Collection<Curriculum> result = this.curriculumRepository.findByHackerId(hackerId);

		return result;
	}

	public Collection<Curriculum> findAllByHackerId(final int hackerId) {

		final Collection<Curriculum> result = this.curriculumRepository.findAllByHackerId(hackerId);

		return result;
	}

	public Boolean security(final int curriculumId) {

		Boolean res = false;

		if (curriculumId != 0) {
			final Hacker hacker = this.hackerService.findByPrincipal();

			final Curriculum curriculum = this.findOne(curriculumId);

			final Collection<Curriculum> curriculums = this.findByHackerId(hacker.getId());

			if (curriculums.contains(curriculum))
				res = true;
		}

		return res;
	}

	public Boolean exist(final int curriculumId) {

		Boolean res = false;

		final Curriculum curriculum = this.curriculumRepository.findOne(curriculumId);

		if (curriculum != null)
			res = true;

		return res;
	}

	public Curriculum copyCurriculum(final Curriculum curriculum) {

		final Curriculum res = new Curriculum();

		res.setEducationDatas(curriculum.getEducationDatas());
		res.setHacker(curriculum.getHacker());
		res.setMiscellaneousDatas(curriculum.getMiscellaneousDatas());
		res.setNoCopy(false);
		res.setPersonalData(curriculum.getPersonalData());
		res.setPositionDatas(curriculum.getPositionDatas());

		final Curriculum copy = this.save(res);

		return copy;
	}

	public Curriculum findByPersonalDataId(final int personalDataId) {

		final Curriculum result = this.curriculumRepository.findByPersonalDataId(personalDataId);

		return result;
	}

	public Curriculum findByPositionDataId(final int positionDataId) {

		final Curriculum result = this.curriculumRepository.findByPositionDataId(positionDataId);

		return result;
	}

	public Curriculum findByEducationDataId(final int educationDataId) {

		final Curriculum result = this.curriculumRepository.findByEducationDataId(educationDataId);

		return result;
	}

	public Curriculum findByMiscellaneousDataId(final int miscellanousDataId) {

		final Curriculum result = this.curriculumRepository.findByMiscellaneousDataId(miscellanousDataId);

		return result;
	}
}
