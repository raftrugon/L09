package controllers.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.RendezvousService;
import services.RsvpService;
import utilities.internal.SchemaPrinter;
import domain.Announcement;
import domain.Rendezvous;
import domain.Rsvp;

@RestController
@RequestMapping("/user/ajax")
public class UserAjaxController {

	@Autowired
	private RsvpService 				rsvpService;
	@Autowired
	private AnnouncementService 				announcementService;
	@Autowired
	private RendezvousService rendezvousService;
	@Autowired
	private Validator			validator;


	@RequestMapping(value="/newAnswer", method = RequestMethod.POST)
	public String newAnser(final int rsvpId, final String question, final String answer){
		try{
			Rsvp rsvp = this.rsvpService.findOne(rsvpId);
			Map<String,String> newMap = rsvp.getQuestionsAndAnswers();
			newMap.put(question, answer);
			rsvp.setQuestionsAndAnswers(newMap);
			this.rsvpService.save(rsvp);
			return "1";
		}catch(Throwable oops){
			return "2";
		}
	}

	@RequestMapping(value = "rendezvous/qa/edit", method = RequestMethod.GET)
	public ModelAndView editQA(@RequestParam(required=true)final int rendezvousId) {
		ModelAndView result = new ModelAndView("rendezvous/qa/edit");
		Rendezvous r = this.rendezvousService.findOne(rendezvousId);
		result.addObject("questions",r.getQuestions());
		result.addObject("rendezvous", r);
		return result;
	}

	@RequestMapping(value = "rendezvous/qa/edit", method = RequestMethod.POST)
	public String editQASave(final int rendezvousId, @RequestParam(value="questions[]") final ArrayList<String> questions) {
		Rendezvous r = this.rendezvousService.findOne(rendezvousId);
		SchemaPrinter.print(questions);
		try{
			r.setQuestions(questions);
			this.rendezvousService.save(r);
			return "1";
		} catch(Throwable oops){
			return "2";
		}
	}

	@RequestMapping(value = "announcement/save", method = RequestMethod.POST)
	public String save(final Announcement announcement, final BindingResult binding) {
		Announcement res = this.announcementService.reconstructNew(announcement, binding);
		if (binding.hasErrors())
			return "0";
		else
			try {
				this.announcementService.save(res);
				return "1";
			} catch (Throwable oops) {
				return "2";
			}
	}

	@RequestMapping(value="rsvp/create", method = RequestMethod.GET)
	public ModelAndView createRSVP(@RequestParam(required=true) final int rendezvousId){
		try{
			Rsvp rsvp = this.rsvpService.create(rendezvousId);
			ModelAndView result = new ModelAndView("rsvp/edit");
			result.addObject("rsvp",rsvp);
			return result;
		}catch(Throwable oops){
			return new ModelAndView("ajaxException");
		}
	}

	@RequestMapping(value="rsvp/save", method = RequestMethod.POST)
	public String saveRSVP(final Rsvp rsvp, final BindingResult binding){
		if(rsvp.getQuestionsAndAnswers() == null)
			rsvp.setQuestionsAndAnswers(new HashMap<String,String>());
		this.validator.validate(rsvp,binding);
		if(binding.hasErrors())
			return "0";
		try{
			this.rsvpService.save(rsvp);
			return "1";
		}catch(Throwable oops){
			return "2";
		}
	}

	@RequestMapping(value="rsvp/createWithoutQuestions", method = RequestMethod.POST)
	public String createWithoutQuestions(final int rendezvousId){
		try{
			this.rsvpService.save(this.rsvpService.create(rendezvousId));
			return "1";
		}catch(Throwable oops){
			oops.printStackTrace();
			return "2";
		}
	}

	@RequestMapping(value="rsvp/cancelRSVP", method = RequestMethod.POST)
	public String cancelRSVP(final int rendezvousId){
		try{
			this.rsvpService.delete(rendezvousId);
			return "1";
		}catch(Throwable oops){
			oops.printStackTrace();
			return "2";
		}
	}

	@RequestMapping(value="rendezvous/link", method = RequestMethod.POST)
	public String linkRendezvous(final int sourceId, final int targetId){
		try{
			this.rendezvousService.link(sourceId, targetId);
			return "1";
		}catch(Throwable oops){
			return "2";
		}
	}

	@RequestMapping(value="rendezvous/link/delete", method = RequestMethod.POST)
	public String deleteLink(final int rendezvousId, final int linkId) {
		try{
			this.rendezvousService.deleteLink(rendezvousId, linkId);
			return "1";
		} catch (Throwable oops) {
			return "2";
		}
	}
}
