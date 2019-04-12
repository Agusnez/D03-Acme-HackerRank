
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.hacker.id = ?1 and a.status = 'ACCEPTED' and a.position.deadline > CURRENT_DATE")
	Collection<Application> findAllAcceptedByHacker(int hackerId);

	@Query("select a from Application a where a.hacker.id = ?1 and a.status = 'REJECTED' and a.position.deadline > CURRENT_DATE")
	Collection<Application> findAllRejectedByHacker(int hackerId);

	@Query("select a from Application a where a.hacker.id = ?1 and a.status = 'PENDING' and a.position.deadline > CURRENT_DATE")
	Collection<Application> findAllPendingByHacker(int hackerId);

	@Query("select a from Application a where a.hacker.id = ?1 and a.status = 'SUBMITTED' and a.position.deadline > CURRENT_DATE")
	Collection<Application> findAllSubmittedByHacker(int hackerId);

	@Query("select a from Application a where a.hacker.id = ?1 and a.position.deadline < CURRENT_DATE")
	Collection<Application> findAllDeadLinePastByHacker(int hackerId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'ACCEPTED' and a.position.deadline > CURRENT_DATE")
	Collection<Application> findAllAcceptedByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'REJECTED' and a.position.deadline > CURRENT_DATE")
	Collection<Application> findAllRejectedByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'PENDING' and a.position.deadline > CURRENT_DATE")
	Collection<Application> findAllPendingByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'SUBMITTED' and a.position.deadline > CURRENT_DATE")
	Collection<Application> findAllSubmittedByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.position.deadline < CURRENT_DATE")
	Collection<Application> findAllDeadLinePastByCompany(int companyId);

	@Query("select a from Application a where a.position.id = ?1")
	Collection<Application> findByPositionId(final int positionId);

	@Query("select a from Application a where a.hacker.id = ?1")
	Collection<Application> findByHackerId(final int hackerId);

}
