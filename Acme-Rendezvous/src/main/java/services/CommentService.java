
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Admin;
import domain.Comment;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentRepository			commentRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService userService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private RendezvousService rendezvousService;

	// Simple CRUD methods ----------------------------------------------------

	public Comment createComment(final int rendezvousId) {
		Comment res = new Comment();
		User u = this.userService.findByPrincipal();
		Assert.isTrue(rendezvousId != 0);
		Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		Assert.notNull(rendezvous);
		Assert.notNull(u);

		res.setRendezvous(rendezvous);
		res.setReplies(new ArrayList<Comment>());
		res.setCreationMoment(new Date(System.currentTimeMillis()-1000));
		res.setUser(u);
		res.setInappropriate(false);

		return res;
	}

	public Comment createReply(final int commentId){
		Comment res = new Comment();
		User u = this.userService.findByPrincipal();
		Comment aux = this.commentRepository.findOne(commentId);


		Assert.notNull(aux);
		Assert.notNull(u);
		Assert.isNull(aux.getReplyingTo());
		Assert.isTrue(this.rendezvousService.getRSVPRendezvousesForUser(u).contains(aux.getRendezvous()));

		res.setRendezvous(null);
		res.setReplies(null);
		res.setCreationMoment(new Date(System.currentTimeMillis()-1000));
		res.setReplyingTo(aux);
		res.setUser(u);
		res.setInappropriate(false);

		return res;
	}

	public Comment findOne(final int commentId) {
		Assert.isTrue(commentId != 0);
		Comment res = this.commentRepository.findOne(commentId);
		Assert.notNull(res);
		return res;
	}

	public Comment save(final Comment comment) {
		Assert.notNull(comment);
		User u = this.userService.findByPrincipal();
		Assert.notNull(u);

		Assert.isTrue(comment.getUser().equals(u));					//#
		Assert.isTrue(comment.getId() == 0);						//#

		Assert.notNull(comment.getText());							//#
		Assert.isTrue(!comment.getText().isEmpty());				//#



		Assert.isTrue(comment.getReplyingTo() == null ^ comment.getRendezvous() == null);

		if(comment.getReplyingTo() == null){
			Assert.notNull(comment.getRendezvous());
			Assert.isTrue(this.rendezvousService.getRSVPRendezvousesForUser(u).contains(comment.getRendezvous()));
		}

		if(comment.getRendezvous() == null){
			Assert.notNull(comment.getReplyingTo());
			Assert.isTrue(this.rendezvousService.getRSVPRendezvousesForUser(u).contains(comment.getReplyingTo().getRendezvous())); 	//#
		}


		comment.setCreationMoment(new Date(System.currentTimeMillis()-1000));

		return this.commentRepository.save(comment);
	}

	public Comment deleteByAdmin(final Comment comment) {
		Admin a = this.adminService.findByPrincipal();
		Assert.notNull(a);
		comment.setInappropriate(true);
		return this.commentRepository.save(comment);
	}

	//Other Business Methods --------------------------------

	public Double[] getCommentRepliesStats() {
		return this.commentRepository.getCommentRepliesStats();
	}

	public Collection<Comment> getRendezvousCommentsSorted(final int rendezvousId) {
		return this.commentRepository.getRendezvousCommentsSorted(rendezvousId);
	}
}
