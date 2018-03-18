package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rendezvous;
import domain.Request;
import domain.User;
import domain.Zervice;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer>{

	@Query(value="select request.* from request join rendezvous on request.rendezvous_id = rendezvous.id where rendezvous.user_id = ?1 order by request.creationMoment DESC limit 1",nativeQuery=true)
	Request findLastForUser(int principalId);

	@Query("select r from Rendezvous r where r not in(select req.rendezvous from Request req where req.zervice = ?1) and r.user = ?2 and r.inappropriate is false and r.deleted is false and r.organisationMoment > CURRENT_TIMESTAMP")
	Collection<Rendezvous> selectRequestableRendezvousesForService(Zervice z,User principal);

	@Query("select z from Zervice z where z not in(select req.zervice from Request req where req.rendezvous = ?1) and z.inappropriate is false")
	Collection<Zervice> selectRequestableServicesForRendezvous(Rendezvous r);
}
