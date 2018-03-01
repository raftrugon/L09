
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import domain.Rendezvous;
import domain.Rsvp;

@Controller
@RequestMapping("")
public class RsvpController extends AbstractController {

	@Autowired
	private RendezvousService	rendezvousService;
	
	//List users who have RSVP a Rendezvous (R20)
	@RequestMapping("/user-who-have-rsvp")
	public ModelAndView usersWhoHaveRSVP(@RequestParam int rendezvousId){
		ModelAndView result;
		
		Rendezvous rendezvous = rendezvousService.findOne(rendezvousId);
		Collection<Rsvp> rsvps = rendezvous.getRsvps();
		
		result = new ModelAndView("rsvp/users-who-have-rsvp");
		result.addObject("rsvps", rsvps);
		result.addObject("requestUri", "user-who-have-rsvp.do");
		return result;
	}
}
