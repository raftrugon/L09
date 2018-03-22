package controllers.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.CategoryService;
import services.CommentService;
import services.RendezvousService;
import services.ZerviceService;
import domain.Announcement;
import domain.Category;
import domain.Comment;
import domain.Rendezvous;
import domain.Zervice;

@RestController
@RequestMapping("/admin/ajax")
public class AdminAjaxController {

	@Autowired
	private AnnouncementService 				announcementService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private RendezvousService rendezvousService;
	@Autowired
	private ZerviceService zerviceService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value="/rendezvous/delete", method=RequestMethod.POST)
	public String deleteRendezvous(@RequestParam(required = true) int rendezvousId) {
		Rendezvous r = rendezvousService.findOne(rendezvousId);
		try{
			rendezvousService.deleteByAdmin(r);
			return "1";
		} catch(Throwable oops) {
			return "2";
		}
	}

	@RequestMapping(value="/announcement/delete", method=RequestMethod.POST)
	public String deleteAnnouncement(@RequestParam(required = true) int announcementId) {
		Announcement a = announcementService.findOne(announcementId);
		try{
			announcementService.deleteByAdmin(a);
			return "1";
		} catch(Throwable oops) {
			return "2";
		}
	}

	@RequestMapping(value="/comment/delete", method=RequestMethod.POST)
	public String deleteComment(@RequestParam(required = true) int commentId) {
		Comment c = commentService.findOne(commentId);
		try{
			commentService.deleteByAdmin(c);
			return "1";
		} catch(Throwable oops) {
			return "2";
		}
	}

	@RequestMapping(value="/zervice/delete", method=RequestMethod.POST)
	public String deleteZervice(@RequestParam(required = true) int zerviceId) {
		try{
			zerviceService.deleteByAdmin(zerviceId);
			return "1";
		} catch(Throwable oops) {
			return "2";
		}
	}

	@RequestMapping(value="/category/edit", method=RequestMethod.GET)
	public ModelAndView editCategory(@RequestParam(required = false) Integer categoryId) {
		ModelAndView res = new ModelAndView("category/edit");
		if(categoryId!=null)
			res.addObject("category", categoryService.findOne(categoryId));
		else
			res.addObject("category", categoryService.create());
		return res;
	}

	@RequestMapping(value="/dashboard/topzervices", method=RequestMethod.GET)
	public ModelAndView topZervices(@RequestParam(required=true)int pageNumber,@RequestParam(required=true)int pageSize){
		ModelAndView res;
		try{
			res = new ModelAndView("admin/topzervices");
			Page<Zervice> page = zerviceService.getTopSellingZervices(pageNumber, pageSize);
			res.addObject("zervices",page.getContent());
			res.addObject("totalPages",page.getTotalPages());
			res.addObject("isFirst",page.isFirstPage());
			res.addObject("isLast",page.isLastPage());
			res.addObject("pageNumber",page.getNumber());
			res.addObject("pageSize",pageSize);
		}catch(Throwable oops){
			res = new ModelAndView("ajaxException");
		}
		return res;
	}


	@RequestMapping(value="category/save", method=RequestMethod.POST)
	public String save(@ModelAttribute final Category category,BindingResult binding) {
		String result;
		Category resCategory = categoryService.reconstruct(category, binding);
		if(binding.hasErrors())
			result="0";
		else
			try {
				categoryService.save(resCategory);
				result ="1";
			} catch (Throwable oops) {
				oops.printStackTrace();
				result = "2";
			}
		return result;
	}
	@RequestMapping(value="category/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(required=true) final int categoryId) {
		String result;
		try {
			categoryService.delete(categoryService.findOne(categoryId));
			result ="1";
		} catch (Throwable oops) {
			oops.printStackTrace();
			result = "2";
		}
		return result;
	}
}
