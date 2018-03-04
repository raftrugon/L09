
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import services.RendezvousService;
import services.UserService;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("")
public class UserController extends AbstractController {

	@Autowired
	private UserService			userService;

	@Autowired
	private RendezvousService	rendezvousService;


	//Constructor
	public UserController() {
		super();
	}

	//List
	@RequestMapping("/user-list")
	public ModelAndView list() {
		ModelAndView result;
		final List<User> users = new ArrayList<User>(this.userService.findAll());
		result = new ModelAndView("user-list");
		result.addObject("users", users);
		result.addObject("requestUri", "user-list.do");
		return result;
	}

	//Display
	@RequestMapping("/user-display")
	public ModelAndView display(@RequestParam(required = true) final int userId) {
		ModelAndView result;
		final User user = this.userService.findOne(userId);
		Collection<Rendezvous> rendezvouses = this.rendezvousService.getRSVPRendezvousesForUser(user);

		result = new ModelAndView("user-display");
		result.addObject("user", user);
		if (!rendezvouses.isEmpty())
			result.addObject("rendezvouses", rendezvouses);

		result.addObject("requestUri", "user-display.do?userId=" + userId);

		return result;
	}


}
