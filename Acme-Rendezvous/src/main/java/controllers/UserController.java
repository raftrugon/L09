
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

	//Create Edit GET
	@RequestMapping(value = "/register/user", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			result = this.newEditModelAndView(userService.create());
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	//Save Delete POST
	@RequestMapping(value = "/register/user", method = RequestMethod.POST, params = "save")
	public ModelAndView save(User user, final BindingResult binding) {
		ModelAndView result;
		user = this.userService.reconstruct(user, binding);
		if (binding.hasErrors()) {
			result = this.newEditModelAndView(user);
		} else
			try {
				User saved = this.userService.save(user);
				result = new ModelAndView("redirect:../user-display.do?userId=" + saved.getId());
			} catch (Throwable oops) {
				result = this.newEditModelAndView(user);
				result.addObject("message", "user.commitError");
			}
		return result;
	}

	//EditModelAndView
	protected ModelAndView newEditModelAndView(final User user) {
		ModelAndView result;
		result = this.newEditModelAndView(user, null);
		return result;
	}

	protected ModelAndView newEditModelAndView(final User user, final String message) {
		ModelAndView result;
		result = new ModelAndView("register/user");
		result.addObject("user", user);
		result.addObject("message", message);
		result.addObject("actionUri", "user/save.do");
		return result;
	}
}
