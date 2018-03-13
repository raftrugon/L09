package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Integer>{


	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccount(int id);

	@Query("select m from Manager m where m.zervices.size > (select avg(x.zervices.size) from Manager x) order by m.zervices.size DESC")
	Collection<Manager> getManagersWhoProvideMoreServicesThanAvg();

	@Query(value="select manager.id,sum(zervice.inappropriate) from manager join zervice on manager.id = zervice.manager_id group by manager.id order by sum(zervice.inappropriate) desc limit ?1",nativeQuery=true)
	List<Object[]> getManagersWithMoreCancelledZervices(int limit);


}
