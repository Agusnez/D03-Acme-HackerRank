
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.CurriculumRepository;
import security.Authority;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Curriculum;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;
import forms.CreateCurriculumForm;

@Service
@Transactional
public class CurriculumService {

	// Managed Repository ------------------------
	@Autowired
	private CurriculumRepository		curriculumRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private HackerService				hackerService;

	@Autowired
	private ApplicationService			applicationService;

	@Autowired
	private CompanyService				companyService;

	@Autowired
	private EducationDataService		educationDataService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private PersonalDataService			personalDataService;

	@Autowired
	private PositionDataService			positionDataService;


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

			final Collection<Curriculum> curriculums = this.findAllByHackerId(hacker.getId());

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

		final Collection<EducationData> educationData = curriculum.getEducationDatas();
		final Collection<EducationData> educationDataCopy = new HashSet<>();

		for (final EducationData educationData2 : educationData) {

			final EducationData newEducationData = new EducationData();

			newEducationData.setDegree(educationData2.getDegree());
			newEducationData.setEndDate(educationData2.getEndDate());
			newEducationData.setInstitution(educationData2.getInstitution());
			newEducationData.setMark(educationData2.getMark());
			newEducationData.setStartDate(educationData2.getStartDate());

			final EducationData copyEducationData = this.educationDataService.save(newEducationData);

			educationDataCopy.add(copyEducationData);
		}

		final Collection<MiscellaneousData> miscellaneousData = curriculum.getMiscellaneousDatas();
		final Collection<MiscellaneousData> miscellaneousDataCopy = new HashSet<>();

		for (final MiscellaneousData miscellaneousData2 : miscellaneousData) {

			final MiscellaneousData newMiscellaneousData = new MiscellaneousData();

			newMiscellaneousData.setAttachments(miscellaneousData2.getAttachments());
			newMiscellaneousData.setText(miscellaneousData2.getText());

			final MiscellaneousData copyMiscellaneousData = this.miscellaneousDataService.save(newMiscellaneousData);

			miscellaneousDataCopy.add(copyMiscellaneousData);

		}

		final Collection<PositionData> positionData = curriculum.getPositionDatas();
		final Collection<PositionData> positionDataCopy = new HashSet<>();

		for (final PositionData positionData2 : positionData) {

			final PositionData newPositionData = new PositionData();

			newPositionData.setDescription(positionData2.getDescription());
			newPositionData.setEndDate(positionData2.getEndDate());
			newPositionData.setStartDate(positionData2.getStartDate());
			newPositionData.setTitle(positionData2.getTitle());

			final PositionData copyPositionData = this.positionDataService.save(newPositionData);

			positionDataCopy.add(copyPositionData);

		}

		final PersonalData personalData = curriculum.getPersonalData();

		final PersonalData newPersonalData = new PersonalData();

		newPersonalData.setFullName(personalData.getFullName());
		newPersonalData.setLinkGitHubProfile(personalData.getLinkGitHubProfile());
		newPersonalData.setLinkLinkedInProfile(personalData.getLinkLinkedInProfile());
		newPersonalData.setPhone(personalData.getPhone());
		newPersonalData.setStatement(personalData.getStatement());

		final PersonalData personalDataCopy = this.personalDataService.save(newPersonalData);

		res.setEducationDatas(educationDataCopy);
		res.setHacker(curriculum.getHacker());
		res.setMiscellaneousDatas(miscellaneousDataCopy);
		res.setNoCopy(false);
		res.setPersonalData(personalDataCopy);
		res.setPositionDatas(positionDataCopy);

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

	public void flush() {
		this.curriculumRepository.flush();
	}

	public Boolean securityCompany(final int applicationId) {

		Boolean res = false;

		final Application app = this.applicationService.findOne(applicationId);

		final Company owner = app.getPosition().getCompany();
		final Company login = this.companyService.findByPrincipal();

		if (owner.equals(login))
			res = true;

		return res;
	}
}
