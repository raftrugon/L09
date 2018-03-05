package controllers.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import services.RequestService;
import services.UserService;
import services.ZerviceService;
import domain.Request;

@RestController
@RequestMapping("/user/request")
public class UserRequestController {

	@Autowired
	private RequestService requestService;
	@Autowired
	private UserService userService;
	@Autowired
	private RendezvousService rendezvousService;
	@Autowired
	private ZerviceService zerviceService;

	public UserRequestController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(
			@RequestParam(required = false) final Integer rendezvousId,
			@RequestParam(required = false) final Integer zerviceId) {
		try {
			return newEditModelAndView(rendezvousId, zerviceId,requestService.create());
		} catch (Throwable oops) {
			return new ModelAndView("ajaxException");
		}
	}

	protected ModelAndView newEditModelAndView(Integer rendezvousId,Integer zerviceId, Request request) {
		ModelAndView result = newEditModelAndView(request);
		if (rendezvousId != null) result.addObject("selectedRendezvous",rendezvousService.findOne(rendezvousId));
		if (zerviceId != null) result.addObject("selectedZervice", zerviceService.findOne(zerviceId));
		return result;
	}

	protected ModelAndView newEditModelAndView(Request request){
		ModelAndView result = new ModelAndView("request/create");
		result.addObject("request",request);
		result.addObject("rendezvouses",userService.getRequestableRendezvouses());
		result.addObject("zervices", zerviceService.findAllNotInappropriate());
		return result;
	}
}
