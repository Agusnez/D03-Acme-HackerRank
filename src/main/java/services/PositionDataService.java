
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import security.Authority;
import domain.Actor;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	// Managed Repository ------------------------
	@Autowired
	private PositionDataRepository	positionDataRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;


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
}
