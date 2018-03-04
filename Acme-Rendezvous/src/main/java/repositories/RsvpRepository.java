
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rsvp;
import domain.User;

@Repository
public interface RsvpRepository extends JpaRepository<Rsvp, Integer> {
	
	@Query("select r.rendezvous from Rsvp r where r.user = ?1")
	Collection<String> getPendingQuestions(Rsvp rsvp);

	@Query("select r from Rsvp r where r.rendezvous.id = ?1 and r.user = ?2")
	Rsvp findForUserAndRendezvous(int rendezvousId, User u);

}