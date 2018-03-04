
package controllers.User;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import controllers.AbstractController;
import domain.Comment;

@RestController
@RequestMapping("/user/comment")
public class UserCommentController extends AbstractController {

	@Autowired
	private CommentService	commentService;


	//Constructor
	public UserCommentController() {
		super();
	}
	
//	@RequestMapping(value = "/createComment", method = RequestMethod.GET)
//	public ModelAndView create(@RequestParam(required = true) final int rendezvousId) {
//		ModelAndView result;
//		try {
//			Comment comment = commentService.createComment(rendezvousId);
//			result = newEditModelAndView(comment);
//		} catch (Throwable oops) {
//			result = new ModelAndView("ajaxException");
//		}
//		return result;
//	}	
	@RequestMapping(value = "/replyComment", method = RequestMethod.GET)
	public ModelAndView createReply(@RequestParam(required = true) final int commentId) {
		ModelAndView result;
		try {
			Comment comment = commentService.createReply(commentId);
			result = newEditModelAndView(comment);
		} catch (Throwable oops) {
			result = new ModelAndView("ajaxException");
		}
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute @Valid final Comment comment, final BindingResult binding) {
		String result;
		if (binding.hasErrors())
			result = "0";
		else
			try {
				commentService.save(comment);
				result = "1";
			} catch (Throwable oops) {
				result = "2";
			}
		return result;
	}

	protected ModelAndView newEditModelAndView(final Comment comment) {
		ModelAndView result;
		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);
		result.addObject("actionUri", "user/comment/save.do");
		return result;
	}
}