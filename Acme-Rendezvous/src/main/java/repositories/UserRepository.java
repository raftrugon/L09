
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccount(int id);
	
	@Query("select count (r) from Rsvp r where r.rendezvous.id = ?1 and r.user = ?2")
	int isRsvpd(int rendezvousId, User findByPrincipal);


}