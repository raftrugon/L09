package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ZerviceService;
import domain.Zervice;

@Controller
@RequestMapping("/zervice")
public class ZerviceController extends AbstractController {

	@Autowired
	private ZerviceService zerviceService;
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result = new ModelAndView("zervice/list");
		result.addObject("zervices", zerviceService.findAll());
		result.addObject("requestUri", "zervice/list.do");
		return result;
	}
}
