package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("announcement")
public class AnnouncementController extends AbstractController{

	//Constructor
	public AnnouncementController() {
		super();
	}
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result = new ModelAndView("announcement/list");
		result.addObject("requestUri", "announcement/list.do");
		return result;
	}

}
