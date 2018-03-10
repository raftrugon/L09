package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.CategoryService;
import services.CommentService;
import services.RendezvousService;
import services.RsvpService;
import services.UserService;
import domain.Category;
import domain.Rendezvous;
import domain.Rsvp;
import domain.User;

@RestController
@RequestMapping("/ajax")
public class AjaxController {

	@Autowired
	private RsvpService 				rsvpService;
	@Autowired
	private AnnouncementService 				announcementService;
	@Autowired
	private UserService					userService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private RendezvousService rendezvousService;
	@Autowired
	private CategoryService	categoryService;


	@RequestMapping(value = "/qa", method = RequestMethod.GET)
	public ModelAndView qa(@RequestParam(required=true)final int rsvpId) {
		ModelAndView result = new ModelAndView("rsvp/qa");
		Rsvp rsvp = rsvpService.findOne(rsvpId);
		result.addObject("qa",rsvp.getQuestionsAndAnswers());
		result.addObject("pendingQuestions", rsvpService.getPendingQuestions(rsvp));
		result.addObject("rsvp", rsvp);
		return result;
	}

	@RequestMapping(value="user-card", method = RequestMethod.GET)
	public ModelAndView userCard(@RequestParam(required=true) final int userId){
		ModelAndView result;
		try{
			User u = userService.findOne(userId);
			result = new ModelAndView("user/card");
			result.addObject("user",u);
		}catch(Throwable oops){
			result = null;
		}
		return result;
	}

	@RequestMapping(value="loadAnnouncements", method = RequestMethod.GET)
	public ModelAndView loadAnnouncements(@RequestParam(required=true) final int type){
		ModelAndView result = new ModelAndView("announcement/subList");
		if(type==0)
			result.addObject("announcements",announcementService.findAllOrderedNotInappropriate());
		else
			try{
				User u = userService.findByPrincipal();
				if(type==1)
					result.addObject("announcements",announcementService.getMyAnnouncementsNotInappropriate(u));
				else if(type==2)
					result.addObject("announcements",announcementService.getRSVPAnnouncementsForUserNotInappropriate(u));
				else
					result.addObject("announcements",announcementService.findAllOrderedNotInappropriate());
			}catch(Throwable oops){
				result.addObject("announcements",announcementService.findAllOrderedNotInappropriate());
			}
		return result;
	}

	@RequestMapping(value="showComments", method = RequestMethod.GET)
	public ModelAndView showComments(@RequestParam(required=true) final int rendezvousId){
		ModelAndView result = new ModelAndView("comment/display");
		Boolean rsvpd = false;
		try{
			rsvpd = userService.isRsvpd(rendezvousId);
			if(rsvpd) result.addObject("newComment",commentService.createComment(rendezvousId));
		}catch(Throwable oops){}
		try{
			Rendezvous rendezvous = rendezvousService.findOne(rendezvousId);
			result.addObject("rendezvous",rendezvous);
			result.addObject("rsvpd",rsvpd);
			result.addObject("comments",commentService.getRendezvousCommentsSorted(rendezvousId));
		}catch(Throwable oops){
			result = new ModelAndView("ajaxException");
		}
		return result;
	}

	@RequestMapping(value="showAnnouncements", method = RequestMethod.GET)
	public ModelAndView showAnnouncements(@RequestParam(required=true) final int rendezvousId){
		ModelAndView result = new ModelAndView("announcement/display");
		try{
			result.addObject("announcements",announcementService.getRendezvousAnnouncementsSorted(rendezvousId));
		}catch(Throwable oops){
			result = new ModelAndView("ajaxException");
		}
		return result;
	}

	@RequestMapping(value="showButtons", method = RequestMethod.GET)
	public ModelAndView showButtons(@RequestParam(required=true) final int rendezvousId){
		ModelAndView result = new ModelAndView("rendezvous/buttonsalerts");
		Boolean rsvpd = false;
		Boolean isAdult = false;
		try{
			rsvpd = userService.isRsvpd(rendezvousId);
			isAdult = userService.isAdult();
		}catch(Throwable oops){}
		try{
			result.addObject("rendezvous",rendezvousService.findOne(rendezvousId));
			result.addObject("rsvpd",rsvpd);
			result.addObject("isAdult",isAdult);
		}catch(Throwable oops){
			result = new ModelAndView("ajaxException");
		}
		return result;
	}

	@RequestMapping(value="rsvp/showChips.do", method = RequestMethod.GET)
	public ModelAndView showCHips(@RequestParam(required=true) final int rendezvousId){
		ModelAndView result = new ModelAndView("rsvp/chips");
		try{
			result.addObject("rsvps",rendezvousService.findOne(rendezvousId).getRsvps());
		}catch(Throwable oops){
			result = new ModelAndView("ajaxException");
		}
		return result;
	}

	@RequestMapping(value = "category/getSubCategories", method = RequestMethod.GET)
	public String getSubCategories(@RequestParam(required=false) final Integer categoryId){
		return categoryService.getCategoriesJson(null).toString();
	}

	@RequestMapping(value = "/rendezvous/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=false,defaultValue="0")final Integer type,@RequestParam(value="categories[]",required=false)final Collection<Category> categories) {
		ModelAndView result = new ModelAndView("rendezvous/subList");
		Collection<Rendezvous> rendezvouss = null;
		try{
			userService.findByPrincipal();
			rendezvouss = rendezvousService.listRendezvouses(type, categories);
		}catch(Throwable oops){
			try{
				rendezvouss = rendezvousService.listRendezvousesAnonymous(categories);
			}catch(Throwable oops2){
				oops2.printStackTrace();
				return new ModelAndView("ajaxException");
			}
		}
		result.addObject("rendezvouss", rendezvouss);
		return result;
	}
}
