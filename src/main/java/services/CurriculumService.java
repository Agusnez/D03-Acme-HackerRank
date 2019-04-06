
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

	public Curriculum reconstruct(final CreateCurriculumForm form, final BindingResult binding) {

		final Hacker actor = this.hackerService.findByPrincipal();

		final Curriculum result = this.create();

		result.setHacker(actor);

		return result;

	}
}
