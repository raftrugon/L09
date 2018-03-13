
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rendezvous;
import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccount(int id);

	@Query("select count (r) from Rsvp r where r.rendezvous.id = ?1 and r.user = ?2")
	int isRsvpd(int rendezvousId, User findByPrincipal);

	@Query("select r from Rendezvous r where r.user = ?1 and r.inappropriate is false and r.deleted is false and r.organisationMoment > CURRENT_TIMESTAMP")
	Collection<Rendezvous> getRequestableRendezvouses(User findByPrincipal);

	//@Query("select r from Rendezvous r where r.user = ?1 and r.inappropriate is false and r.deleted is false and r.organisationMoment > CURRENT_TIMESTAMP")
	//Collection<Rendezvous> getRequestableRendezvousesForZervice(User findByPrincipal,Zervice zervice);



}