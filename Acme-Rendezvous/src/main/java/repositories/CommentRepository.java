
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select coalesce(avg(c.replies.size),0), coalesce(stddev(c.replies.size),0) from Comment c")
	Double[] getCommentRepliesStats();

	@Query("select r.comments from Rendezvous r where r.id = ?1 order by creationMoment DESC")
	Collection<Comment> getRendezvousCommentsSorted(int rendezvousId);
}