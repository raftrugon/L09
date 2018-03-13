package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Manager;
import domain.User;

import services.ManagerService;
import services.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {
	
	@Autowired
	private UserService			userService;
	@Autowired
	private ManagerService		managerService;
	
	
	//Create Edit GET
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required=false,defaultValue="0")final int mode) {
		ModelAndView result;
		try {
			result = this.newEditModelAndView(userService.create(),managerService.create(),mode);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	//Save Delete POST
	@RequestMapping(value = "user", method = RequestMethod.POST, params = "save")
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
	
	//Manager Save POST
	//Save Delete POST
	@RequestMapping(value = "manager", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Manager manager, final BindingResult binding) {
		ModelAndView result;
		manager = this.managerService.reconstruct(manager, binding);
		if (binding.hasErrors()) {
			result = this.newEditModelAndView(manager);
		} else
			try {
				Manager saved = this.managerService.save(manager);
				result = new ModelAndView("redirect:../");
			} catch (Throwable oops) {
				result = this.newEditModelAndView(manager);
				result.addObject("message", "user.commitError");
			}
		return result;
	}

	//EditModelAndView
	protected ModelAndView newEditModelAndView(final User user,final Manager manager,final int mode) {
		ModelAndView result;
		result = this.newEditModelAndView(user,manager, null,mode);
		return result;
	}
	
	protected ModelAndView newEditModelAndView(final User user) {
		ModelAndView result;
		result = this.newEditModelAndView(user,managerService.create(), null,0);
		return result;
	}
	
	protected ModelAndView newEditModelAndView(final Manager manager) {
		ModelAndView result;
		result = this.newEditModelAndView(userService.create(),manager, null,1);
		return result;
	}

	protected ModelAndView newEditModelAndView(final User user,final Manager manager, final String message, final int mode) {
		ModelAndView result;
		result = new ModelAndView("register/user");
		result.addObject("user", user);
		result.addObject("manager", manager);
		result.addObject("message", message);
		result.addObject("mode",mode);
		result.addObject("actionUri", "user/save.do");
		return result;
	}

}
