
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

	public Comment createComment(int rendezvousId) {
		Comment res = new Comment();
		User u = userService.findByPrincipal();
		Assert.isTrue(rendezvousId != 0);
		Rendezvous rendezvous = rendezvousService.findOne(rendezvousId);
		Assert.notNull(rendezvous);
		Assert.notNull(u);
		
		res.setRendezvous(rendezvous);
		res.setReplies(new ArrayList<Comment>());
		res.setCreationMoment(new Date(System.currentTimeMillis()-1000));
		res.setUser(u);
		res.setinappropriate(false);

		return res;
	}
	
	public Comment createReply(int commentId){
		Comment res = new Comment();
		User u = userService.findByPrincipal();
		Comment aux = commentRepository.findOne(commentId);
		

		Assert.notNull(aux);
		Assert.notNull(u);
		Assert.isNull(aux.getReplyingTo());
		Assert.isTrue(rendezvousService.getRSVPRendezvousesForUser(u).contains(aux.getRendezvous()));
		
		res.setRendezvous(null);
		res.setReplies(null);
		res.setCreationMoment(new Date(System.currentTimeMillis()-1000));
		res.setReplyingTo(aux);
		res.setUser(u);
		res.setinappropriate(false);
		
		return res;
	}

	public Comment findOne(int commentId) {
		Assert.isTrue(commentId != 0);
		Comment res = commentRepository.findOne(commentId);
		Assert.notNull(res);
		return res;
	}

	public Comment save(Comment comment) {
		User u = userService.findByPrincipal();
		Assert.notNull(comment);
		Assert.notNull(u);
		comment.setCreationMoment(new Date(System.currentTimeMillis()-1000));
		if(comment.getReplyingTo() == null){
			Assert.notNull(comment.getRendezvous());
			Assert.isTrue(rendezvousService.getRSVPRendezvousesForUser(u).contains(comment.getRendezvous()));
		}
		if(comment.getRendezvous() == null){
			Assert.notNull(comment.getReplyingTo());
		}
		Assert.isTrue(comment.getReplyingTo() == null ^ comment.getRendezvous() == null);
		return commentRepository.save(comment);
	}
	
	public Comment deleteByAdmin(final Comment comment) {
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		comment.setinappropriate(true);
		return commentRepository.save(comment);
	}
	
	//Other Business Methods --------------------------------
	
	public Double[] getCommentRepliesStats() {
		return commentRepository.getCommentRepliesStats();
	}

	public Collection<Comment> getRendezvousCommentsSorted(int rendezvousId) {
		return commentRepository.getRendezvousCommentsSorted(rendezvousId);
	}
}
