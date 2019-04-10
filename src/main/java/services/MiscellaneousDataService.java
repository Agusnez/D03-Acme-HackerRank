
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MiscellaneousDataRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;

@Service
@Transactional
public class MiscellaneousDataService {

	// Managed Repository ------------------------
	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private Validator					validator;


	// Simple CRUD methods -----------------------

	public MiscellaneousData create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HACKER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		MiscellaneousData result;
		result = new MiscellaneousData();

		return result;
	}

	public Collection<MiscellaneousData> findAll() {

		Collection<MiscellaneousData> result;

		result = this.miscellaneousDataRepository.findAll();

		return result;
	}

	public MiscellaneousData findOne(final int miscellaneousId) {

		Assert.notNull(miscellaneousId);
		MiscellaneousData result;
		result = this.miscellaneousDataRepository.findOne(miscellaneousId);
		return result;
	}

	public MiscellaneousData save(final MiscellaneousData miscellaneous) {

		Assert.notNull(miscellaneous);

		MiscellaneousData result;

		result = this.miscellaneousDataRepository.save(miscellaneous);

		return result;

	}

	public void delete(final MiscellaneousData miscellaneous) {

		Assert.notNull(miscellaneous);

		this.miscellaneousDataRepository.delete(miscellaneous);

	}

	public MiscellaneousData reconstruct(final MiscellaneousDataForm form, final BindingResult binding) {

		final MiscellaneousData result = this.create();

		this.validator.validate(form, binding);

		result.setId(form.getId());
		result.setVersion(form.getVersion());
		result.setAttachments(form.getAttachments());

		return result;

	}

	public Boolean exist(final int positionId) {

		Boolean res = false;

		if (positionId != 0) {
			final MiscellaneousData miscellaneous = this.miscellaneousDataRepository.findOne(positionId);

			if (miscellaneous != null)
				res = true;
		} else
			res = true;

		return res;
	}

	public Boolean security(final int miscellaneousId, final int curriculumId) {

		Boolean res = false;

		if (miscellaneousId != 0) {
			final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

			final MiscellaneousData miscellaneous = this.findOne(miscellaneousId);

			if (curriculum.getPositionDatas().contains(miscellaneous))
				res = true;
		} else
			res = true;

		return res;
	}

}
