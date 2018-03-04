
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;
import domain.User;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

	@Query("select a from Announcement a order by creationMoment DESC")
	Collection<Announcement> findAllOrdered();

	@Query("select r.rendezvous.announcements from Rsvp r where r.user = ?1 order by creationMoment DESC")
	Collection<Announcement> getRSVPAnnouncementsForUser(User user);

	@Query("select r.announcements from Rendezvous r where r.user = ?1 order by creationMoment DESC")
	Collection<Announcement> getMyAnnouncements(User user);

	@Query("select r.announcements from Rendezvous r where r.id = ?1 order by creationMoment DESC")
	Collection<Announcement> getRendezvousAnnouncementsSorted(int rendezvousId);

	//WITHOUT INAPPROPRIATE

	@Query("select a from Announcement a where a.inappropriate = 0 order by creationMoment DESC")
	Collection<Announcement> findAllOrderedNotInappropriate();

	@Query("select a from Rsvp r join r.rendezvous.announcements a where r.user = ?1 AND a.inappropriate = 0 order by creationMoment DESC")
	Collection<Announcement> getRSVPAnnouncementsForUserNotInappropriate(User user);

	@Query("select a from Rendezvous r join r.announcements a where r.user = ?1 AND a.inappropriate = 0 order by creationMoment DESC")
	Collection<Announcement> getMyAnnouncementsNotInappropriate(User user);

	@Query("select a from Rendezvous r join r.announcements a where r.id = ?1 AND a.inappropriate = 0 order by creationMoment DESC")
	Collection<Announcement> getRendezvousAnnouncementsSortedNotInappropriate(int rendezvousId);
}
