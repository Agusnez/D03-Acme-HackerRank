
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
	
	@Query(nativeQuery = true, value= "select avg(p.offered_salary) from `position` p")
	Double avgSalaries();
	
	@Query(nativeQuery = true, value= "select min(p.offered_salary) from `position` p")
	Integer minSalary();
	
	@Query(nativeQuery = true, value= "select max(p.offered_salary) from `position` p")
	Integer maxSalary();
	
	@Query(nativeQuery = true, value= "select std(p.offered_salary) from `position` p")
	Double stdSalaries();
	
	@Query(nativeQuery = true, value= "select p.id from `position` p order by offered_salary asc limit 1")
	int findWorstPosition();
	
	@Query(nativeQuery = true, value= "select p.id from `position` p order by offered_salary desc limit 1")
	int findBestPosition();
	
	@Query(nativeQuery = true, value= "select min(count) from (select count(*) as Count " + 
									"from curriculum c join hacker h on (h.id = c.hacker) " + 
			 						"group by hacker) as counts")
	Integer minNumberOfCurriculaPerHacker();
	
	@Query(nativeQuery = true, value= "select max(count) from (select count(*) as Count " + 
				"from curriculum c join hacker h on (h.id = c.hacker) " + 
				"group by hacker) as counts")
	Integer maxNumberOfCurriculaPerHacker();
	
	@Query(nativeQuery = true, value= "select avg(count) from (select count(*) as Count " + 
				"from curriculum c join hacker h on (h.id = c.hacker)" + 
					"group by hacker) as counts")
	Double avgNumberOfCurriculaPerHacker();
	
	@Query(nativeQuery = true, value= "select std(count) from (select count(*) as Count " + 
				"from curriculum c join hacker h on (h.id = c.hacker) " + 
				"group by hacker) as counts")
	Double stdNumberOfCurriculaPerHacker();
	
	@Query(nativeQuery = true, value= "select min(count) from (" + 
				" select f.id, count(fp.finder) as Count from finder f" + 
				" left join finder_positions fp on (f.id = fp.finder)" + 
				" group by f.id" + 
				" ) as counts")	
	Integer minNumberOfResultsInFinders();
	
	@Query(nativeQuery = true, value= "select max(count) from (" + 
				" select f.id, count(fp.finder) as Count from finder f" + 
				" left join finder_positions fp on (f.id = fp.finder)" + 
				" group by f.id" + 
				" ) as counts")	
	Integer maxNumberOfResultsInFinders();
	
	@Query(nativeQuery = true, value= "select avg(count) from (" + 
				" select f.id, count(fp.finder) as Count from finder f" + 
				" left join finder_positions fp on (f.id = fp.finder)" + 
				" group by f.id" + 
				" ) as counts")	
	Double avgNumberOfResultsInFinders();
	
	@Query(nativeQuery = true, value= "select std(count) from (" + 
				" select f.id, count(fp.finder) as Count from finder f" + 
				" left join finder_positions fp on (f.id = fp.finder)" + 
				" group by f.id" + 
				" ) as counts")	
	Double stdNumberOfResultsInFinders();
	
	
	@Query(nativeQuery = true, value = "select sum(case when Count > 0 then 1 else 0 end)/sum(case when Count < 1 then 1 else 0 end) n from ( " + 
				"select f.id, count(fp.finder) as Count from finder f " + 
				"left join finder_positions fp on (f.id = fp.finder) " + 
				"group by f.id " + 
				") as counts")
	Double ratioEmptyNotEmptyFinders();
		
	
}
