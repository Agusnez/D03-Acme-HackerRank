
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EducationDataRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.EducationData;
import forms.EducationDataForm;

@Service
@Transactional
public class EducationDataService {

	// Managed Repository ------------------------
	@Autowired
	private EducationDataRepository	educationDataRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods -----------------------

	public EducationData create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HACKER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		EducationData result;
		result = new EducationData();

		return result;
	}

	public Collection<EducationData> findAll() {

		Collection<EducationData> result;

		result = this.educationDataRepository.findAll();

		return result;
	}

	public EducationData findOne(final int educationId) {

		Assert.notNull(educationId);
		EducationData result;
		result = this.educationDataRepository.findOne(educationId);
		return result;
	}

	public EducationData save(final EducationData education) {

		Assert.notNull(education);

		EducationData result;

		result = this.educationDataRepository.save(education);

		return result;

	}

	public void delete(final EducationData education) {

		Assert.notNull(education);

		this.educationDataRepository.delete(education);

	}

	public EducationData reconstruct(final EducationDataForm form, final BindingResult binding) {

		final EducationData result = this.create();

		this.validator.validate(form, binding);

		result.setId(form.getId());
		result.setVersion(form.getVersion());
		result.setDegree(form.getDegree());
		result.setInstitution(form.getInstitution());
		result.setMark(form.getMark());
		result.setStartDate(form.getStartDate());
		result.setEndDate(form.getEndDate());

		return result;

	}

	public Boolean exist(final int positionId) {

		Boolean res = false;

		if (positionId != 0) {
			final EducationData education = this.educationDataRepository.findOne(positionId);

			if (education != null)
				res = true;
		} else
			res = true;

		return res;
	}

	public Boolean security(final int educationId, final int curriculumId) {

		Boolean res = false;

		if (educationId != 0) {
			final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

			final EducationData education = this.findOne(educationId);

			if (curriculum.getPositionDatas().contains(education))
				res = true;
		} else
			res = true;

		return res;
	}
}
