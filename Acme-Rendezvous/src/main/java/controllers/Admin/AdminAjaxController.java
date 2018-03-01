package controllers.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import services.AnnouncementService;
import services.CommentService;
import services.RendezvousService;
import domain.Announcement;
import domain.Comment;
import domain.Rendezvous;

@RestController
@RequestMapping("/admin/ajax")
public class AdminAjaxController {

	@Autowired
	private AnnouncementService 				announcementService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private RendezvousService rendezvousService;

	@RequestMapping(value="/rendezvous/delete", method=RequestMethod.POST)
	public String deleteRendezvous(@RequestParam(required = true) int rendezvousId) {
		Rendezvous r = rendezvousService.findOne(rendezvousId);
		try{
			rendezvousService.deleteByAdmin(r);
			return "1";
		} catch(Throwable oops) {
			return "2";
		}
	}	
	
	@RequestMapping(value="/announcement/delete", method=RequestMethod.POST)
	public String deleteAnnouncement(@RequestParam(required = true) int announcementId) {
		Announcement a = announcementService.findOne(announcementId);
		try{
			announcementService.deleteByAdmin(a);
			return "1";
		} catch(Throwable oops) {
			return "2";
		}
	}	
	
	@RequestMapping(value="/comment/delete", method=RequestMethod.POST)
	public String deleteComment(@RequestParam(required = true) int commentId) {
		Comment c = commentService.findOne(commentId);
		try{
			commentService.deleteByAdmin(c);
			return "1";
		} catch(Throwable oops) {
			return "2";
		}
	}
}
