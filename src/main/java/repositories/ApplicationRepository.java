
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.hacker.id = ?1 and a.status = 'ACCEPTED'")
	Collection<Application> findAllAcceptedByHacker(int hackerId);

	@Query("select a from Application a where a.hacker.id = ?1 and a.status = 'REJECTED'")
	Collection<Application> findAllRejectedByHacker(int hackerId);

	@Query("select a from Application a where a.hacker.id = ?1 and a.status = 'PENDING'")
	Collection<Application> findAllPendingByHacker(int hackerId);

	@Query("select a from Application a where a.hacker.id = ?1 and a.status = 'SUBMITTED'")
	Collection<Application> findAllSubmittedByHacker(int hackerId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'ACCEPTED'")
	Collection<Application> findAllAcceptedByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'REJECTED'")
	Collection<Application> findAllRejectedByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'PENDING'")
	Collection<Application> findAllPendingByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'SUBMITTED'")
	Collection<Application> findAllSubmittedByCompany(int companyId);

}
