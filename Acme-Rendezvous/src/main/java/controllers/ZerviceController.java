package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ZerviceService;

@Controller
@RequestMapping("/zervice")
public class ZerviceController extends AbstractController {

	@Autowired
	private ZerviceService zerviceService;

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result = new ModelAndView("zervice/list");
		result.addObject("zervices", this.zerviceService.findAll());
		result.addObject("requestUri", "zervice/list.do");
		result.addObject("locale",LocaleContextHolder.getLocale().getLanguage());
		return result;
	}
}
