
package controllers.User;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.RendezvousService;
import services.RsvpService;
import services.UserService;
import utilities.internal.SchemaPrinter;
import controllers.AbstractController;
import domain.Rendezvous;
import domain.Rsvp;

@Controller
@RequestMapping("/user/rendezvous")
public class UserRendezvousController extends AbstractController {

	@Autowired
	private RendezvousService	rendezvousService;
	@Autowired
	private UserService			userService;
	@Autowired
	private RsvpService			rsvpService;


	//Constructor
	public UserRendezvousController() {
		super();
	}

	@RequestMapping(value = "/answer", method = RequestMethod.POST)
	public ModelAndView answer(@RequestParam final String question, @RequestParam final String rsvpId,
			@RequestParam final String answer) {

		ModelAndView result;

		try{
			int rsvpIdInt = Integer.parseInt(rsvpId);
			Rsvp rsvp = rsvpService.findOne(rsvpIdInt);
			Assert.notNull(rsvp);
			Assert.isTrue(rsvp.getUser().equals(userService.findByPrincipal()));

			rsvp.getQuestionsAndAnswers().put(question, answer);
			rsvpService.save(rsvp);

			result = new ModelAndView("redirect:/rendezvous/display.do?rendezvousId="+rsvp.getRendezvous().getId());
		}
		catch (Throwable oops) {
			result = new ModelAndView("redirect:/rendezvous/list.do");
			result.addObject("message", "rendezvous.commitError");
		}

		return result;
	}


	@RequestMapping(value="/cancel", method = RequestMethod.GET)
	public ModelAndView cancelRendezvous(@RequestParam(required=true)final int rendezvousId, final RedirectAttributes redir){
		ModelAndView result = new ModelAndView("redirect:../../rendezvous/display.do?rendezvousId="+rendezvousId);
		try{
			rendezvousService.deleteByUser(rendezvousId);
		}catch(Throwable oops){
			redir.addFlashAttribute("message","master.page.errors.cancelRendezvousError");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			//UserRendezvousCreateForm rendezvous = new UserRendezvousCreateForm();
			result = newEditModelAndView(rendezvousService.create());
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) final int rendezvousId) {
		ModelAndView result;
		try {
			Rendezvous rendezvous = rendezvousService.findOne(rendezvousId);
			if(rendezvous.getDeleted() || rendezvous.getinappropriate() || rendezvous.getOrganisationMoment().before(new Date()) || rendezvous.getUser() != userService.findByPrincipal() || rendezvous.getFinalMode())
				throw new Throwable();
			result = newEditModelAndView(rendezvous);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/rendezvous/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Rendezvous rendezvous, final BindingResult binding) {
		ModelAndView result;
		Rendezvous saved;
		Rendezvous validatedObject;

		if(rendezvous.getId()==0) validatedObject = rendezvousService.reconstructNew(rendezvous, binding);
		else validatedObject = rendezvousService.reconstruct(rendezvous, binding);

		if (binding.hasErrors()){
			SchemaPrinter.print(binding.getAllErrors());
			result = newEditModelAndView(rendezvous);
		}
		else
			try {
				saved = rendezvousService.save(validatedObject);
				result = new ModelAndView("redirect:../../rendezvous/display.do?rendezvousId=" + saved.getId());
			} catch (Throwable oops) {
				oops.printStackTrace();
				result = newEditModelAndView(rendezvous);
				result.addObject("message", "rendezvous.commitError");
			}
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Rendezvous rendezvous, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = newEditModelAndView(rendezvous);
		else
			try {
				if(rendezvous.getOrganisationMoment().before(new Date()) || rendezvous.getUser() != userService.findByPrincipal() || rendezvous.getFinalMode())
					throw new Throwable();
				rendezvousService.deleteByUser(rendezvous.getId());
				result = new ModelAndView("redirect:../../rendezvous/list.do");
			} catch (Throwable oops) {
				result = newEditModelAndView(rendezvous);
				result.addObject("message", "rendezvous.commitError");
			}
		return result;
	}

	protected ModelAndView newEditModelAndView(final Rendezvous rendezvous) {
		ModelAndView result;
		result = new ModelAndView("user/rendezvous/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("isAdult", userService.isAdult());
		result.addObject("rendezvouses", rendezvousService.getRendezvousesToLink());
		result.addObject("actionUri", "user/rendezvous/save.do");
		return result;
	}
}
