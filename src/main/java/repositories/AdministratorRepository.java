
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Company;
import domain.Hacker;
import domain.Position;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int userAccountId);
	
	@Query(nativeQuery= true, value= "select avg(count) from (select count(*) as Count "
									+ "from `position` p join company c on (c.id = p.company) "
									+ "group by company) as counts")
	Double avgOfPositionsPerCompany();
	
	@Query(nativeQuery=true, value= "select max(count) from (select count(*) as Count "
									+ "from `position` p join company c on (c.id = p.company) "
									+ "group by company) as counts")
	Integer maxPositionOfCompany();
	
	@Query(nativeQuery=true, value= "select min(count) from (select count(*) as Count "
									+ "from `position` p join company c on (c.id = p.company)"
									+ " group by company) as counts")
	Integer minPositionOfCompany();
	
	@Query(nativeQuery=true, value= "select std(count) from (select count(*) as Count "
									+ "from `position` p join company c on (c.id = p.company)"
									+ " group by company) as counts")
	Double stdOfPositionsPerCompany();
	
	@Query(nativeQuery= true, value= "select avg(count) from (select count(*) as Count"
									+ " from application a join hacker h on (h.id = a.hacker)"
									+ " group by hacker) as counts")
	Double avgOfApplicationsPerHacker();
	
	@Query(nativeQuery= true, value= "select max(count) from (select count(*) as Count"
									+ " from application a join hacker h on (h.id = a.hacker)"
									+ " group by hacker) as counts")
	Integer maxApplicationsOfHacker();
	
	@Query(nativeQuery= true, value= "select min(count) from (select count(*) as Count"
									+ " from application a join hacker h on (h.id = a.hacker)"
									+ " group by hacker) as counts")
	Integer minApplicationsOfHacker();
	
	@Query(nativeQuery= true, value= "select std(count) from (select count(*) as Count"
									+ " from application a join hacker h on (h.id = a.hacker)"
									+ " group by hacker) as counts")
	Double stdOfApplicationsPerHacker();
	
	@Query(nativeQuery = true, value= "select c.name  as Count from `position` p "
										+ "join company c on (c.id = p.company) group by company "
										+ "ORDER BY COUNT(*) DESC limit 3")
	List<String> topCompaniesWithMorePositions();
	
	@Query(nativeQuery = true, value= "select h.name  as Count from application a "
										+ "join hacker h on (h.id = a.hacker) group by hacker "
										+ "ORDER BY COUNT(*) DESC limit 3")
	List<String> topHackerWithMoreApplications();
	
	
	//TODO: 
	@Query(nativeQuery = true, value= "select avg(p.offered_salary), min(p.offered_salary), max(p.offered_salary), std(p.offered_salary) from `position` p")
	List<Double> statsSalaries();
	
	@Query(nativeQuery = true, value= "select p.id from `position` p order by offered_salary asc limit 1")
	int findWorstPosition();
	
	@Query(nativeQuery = true, value= "select p.id from `position` p order by offered_salary desc limit 1")
	int findBestPosition();
	
	
}
