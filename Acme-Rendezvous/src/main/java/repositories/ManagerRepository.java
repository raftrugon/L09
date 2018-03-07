package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Integer>{

	
	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccount(int id);

	@Query("select m from Manager m where m.zervices.size > (select avg(x.zervices.size) from Manager x)")
	Collection<Manager> getManagersWhoProvideMoreServicesThanAvg();

//	@Query("")
//	Collection<Manager> getManagersWithMoreCancelledZervices();

	
}
