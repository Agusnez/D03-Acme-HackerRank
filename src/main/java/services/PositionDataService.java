
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionDataRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.PositionData;
import forms.PositionDataForm;

@Service
@Transactional
public class PositionDataService {

	// Managed Repository ------------------------
	@Autowired
	private PositionDataRepository	positionDataRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods -----------------------

	public PositionData create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HACKER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		PositionData result;
		result = new PositionData();

		return result;
	}

	public Collection<PositionData> findAll() {

		Collection<PositionData> result;

		result = this.positionDataRepository.findAll();

		return result;
	}

	public PositionData findOne(final int positionId) {

		Assert.notNull(positionId);
		PositionData result;
		result = this.positionDataRepository.findOne(positionId);
		return result;
	}

	public PositionData save(final PositionData position) {

		Assert.notNull(position);

		PositionData result;

		result = this.positionDataRepository.save(position);

		return result;

	}

	public void delete(final PositionData position) {

		Assert.notNull(position);

		this.positionDataRepository.delete(position);

	}

	public PositionData reconstruct(final PositionDataForm form, final BindingResult binding) {

		final PositionData result = this.create();

		this.validator.validate(form, binding);

		result.setId(form.getId());
		result.setVersion(form.getVersion());
		result.setTitle(form.getTitle());
		result.setDescription(form.getDescription());
		result.setStartDate(form.getStartDate());
		result.setEndDate(form.getEndDate());

		return result;

	}

	public Boolean exist(final int positionId) {

		Boolean res = false;

		if (positionId != 0) {
			final PositionData position = this.positionDataRepository.findOne(positionId);

			if (position != null)
				res = true;
		} else
			res = true;

		return res;
	}

	public Boolean security(final int positionId, final int curriculumId) {

		Boolean res = false;

		if (positionId != 0) {
			final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

			final PositionData position = this.findOne(positionId);

			if (curriculum.getPositionDatas().contains(position))
				res = true;
		} else
			res = true;

		return res;
	}
}
