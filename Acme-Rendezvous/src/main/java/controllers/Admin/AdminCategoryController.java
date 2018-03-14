package controllers.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;


@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController  extends AbstractController {

	@RequestMapping("/list")
	public ModelAndView list() {
		try{
			return new ModelAndView("category/list");
		}catch(Throwable oops){
			return new ModelAndView("ajaxException");
		}
	}
}
