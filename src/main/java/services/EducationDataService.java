
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationDataRepository;
import security.Authority;
import domain.Actor;
import domain.EducationData;

@Service
@Transactional
public class EducationDataService {

	// Managed Repository ------------------------
	@Autowired
	private EducationDataRepository	educationDataRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;


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
}
