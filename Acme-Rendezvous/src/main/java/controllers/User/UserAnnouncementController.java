
package controllers.User;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import controllers.AbstractController;
import domain.Announcement;

@Controller
@RequestMapping("user/announcement")
public class UserAnnouncementController extends AbstractController {

	@Autowired
	private AnnouncementService	announcementService;


	//Constructor
	public UserAnnouncementController() {
		super();
	}

	

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute @Valid final Announcement announcement, final BindingResult binding) {
		String result;
		if (binding.hasErrors())
			result = binding.getAllErrors().toString();
		else
			try {
				announcementService.save(announcement);
				result = "1";
			} catch (Throwable oops) {
				oops.printStackTrace();
				result = "2";
			}
		return result;
	}
	
	protected ModelAndView newEditModelAndView(final Announcement announcement) {
		ModelAndView result;
		result = new ModelAndView("announcement/edit");
		result.addObject("announcement", announcement);
		result.addObject("actionUri", "user/announcement/save.do");
		return result;
	}
}