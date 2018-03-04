package controllers.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ManagerService;
import services.ZerviceService;
import domain.Zervice;

@RestController
@RequestMapping("/manager/zervice")
public class ManagerZerviceController {
	
	@Autowired
	private ManagerService managerService;
	@Autowired
	private ZerviceService zerviceService;
	@Autowired
	private CategoryService	categoryService;
	
	
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			result = this.newEditModelAndView(zerviceService.create());
			result.addObject("categories",categoryService.findAll());
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) final int zerviceId) {
		ModelAndView result;
		try {
			Zervice zervice = this.zerviceService.findOne(zerviceId);
			if(zervice.getInappropriate() || zervice.getManager() != managerService.findByPrincipal())
				throw new Throwable();
			result = this.newEditModelAndView(zervice);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/zervice/list.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Zervice zervice, final BindingResult binding) {
		ModelAndView result;
		Zervice saved;
		Zervice validatedObject;
		
		if(zervice.getId()==0) validatedObject = this.zerviceService.reconstructNew(zervice, binding);
		else validatedObject = this.zerviceService.reconstruct(zervice, binding);
		
		if (binding.hasErrors()) {
			result = newEditModelAndView(zervice);
		} else
			try {
				saved = zerviceService.save(validatedObject);
				result = new ModelAndView("redirect:../../zervice/display.do?zerviceId=" + saved.getId());
			} catch (Throwable oops) {
				oops.printStackTrace();
				result = newEditModelAndView(zervice);
				result.addObject("message", "zervice.commitError");
			}
		return result;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Zervice zervice, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.newEditModelAndView(zervice);
		else
			try {
				this.zerviceService.deleteByUser(zervice.getId());
				result = new ModelAndView("redirect:../../zervice/list.do");
			} catch (Throwable oops) {
				result = this.newEditModelAndView(zervice);
				result.addObject("message", "zervice.commitError");
			}
		return result;
	}
	
	protected ModelAndView newEditModelAndView(final Zervice zervice) {
		ModelAndView result;
		result = new ModelAndView("manager/zervice/edit");
		result.addObject("zervice", zervice);
		result.addObject("actionUri", "manager/zervice/save.do");
		return result;
	}
	
	

}
