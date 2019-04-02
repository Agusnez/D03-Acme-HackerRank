
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import security.Authority;
import domain.Actor;
import domain.Company;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	// Managed repository

	@Autowired
	private ProblemRepository	problemRepository;

	// Suporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods

	public Problem create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		final Problem result = new Problem();

		final Collection<String> attachments = new HashSet<>();
		result.setAttachments(attachments);
		result.setCompany(this.companyService.findByPrincipal());

		result.setFinalMode(false);

		return result;

	}

	public Collection<Problem> findAll() {

		final Collection<Problem> problems = this.problemRepository.findAll();

		Assert.notNull(problems);

		return problems;
	}

	public Problem findOne(final int problemId) {

		final Problem problem = this.problemRepository.findOne(problemId);

		return problem;

	}

	public Problem save(final Problem problem) {
		Assert.notNull(problem);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Authority comp = new Authority();
		comp.setAuthority(Authority.COMPANY);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(comp));
		Assert.isTrue(actor.getId() == problem.getCompany().getId());

		Problem result = problem;

		if (problem.getFinalMode() == false || problem.getId() == 0)
			result = this.save(problem);

		return result;
	}

	public void delete(final Problem problem) {
		Assert.notNull(problem);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Authority comp = new Authority();
		comp.setAuthority(Authority.COMPANY);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(comp));
		Assert.isTrue(actor.getId() == problem.getCompany().getId());

		if (problem.getFinalMode() == false)
			this.problemRepository.delete(problem);

	}

	//Other Business methods----------------------------------------------------
	public Collection<Problem> findProblemByCompanyId(final int companyId) {
		final Collection<Problem> problems = this.problemRepository.findProblemsByCompanyId(companyId);

		return problems;
	}

	public Collection<Problem> findProblemsByPositionId(final int positionId) {
		final Collection<Problem> problems = this.problemRepository.findProblemsByPositionId(positionId);

		return problems;
	}

	public Problem reconstruct(final Problem problem, final BindingResult binding) {

		Problem result = problem;
		final Problem problemNew = this.create();

		if (problem.getId() == 0 || problem == null) {

			problem.setCompany(problemNew.getCompany());

			this.validator.validate(problem, binding);

			result = problem;
		} else {

			final Problem problemBBDD = this.findOne(problem.getId());

			problem.setCompany(problemBBDD.getCompany());

			this.validator.validate(problem, binding);

		}

		return result;

	}

	public Boolean problemCompanySecurity(final int problemId) {
		Boolean res = false;
		final Problem problem = this.findOne(problemId);

		final Company owner = problem.getCompany();

		final Company login = this.companyService.findByPrincipal();

		if (login.equals(owner))
			res = true;

		return res;
	}

	public void flush() {
		this.problemRepository.flush();
	}
}
