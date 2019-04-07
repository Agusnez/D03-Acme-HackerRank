
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousDataRepository;
import security.Authority;
import domain.Actor;
import domain.MiscellaneousData;

@Service
@Transactional
public class MiscellaneousDataService {

	// Managed Repository ------------------------
	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService				actorService;


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

}
