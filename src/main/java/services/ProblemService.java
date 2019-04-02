
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import security.Authority;
import domain.Actor;
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

	public void flush() {
		this.problemRepository.flush();
	}
}
