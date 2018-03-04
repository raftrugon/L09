/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.Admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Rendezvous;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {


	@Autowired
	private RendezvousService rendezvousService;
	@Autowired
	private CommentService commentService;
	
	// Constructors -----------------------------------------------------------

	public AdminController() {
		super();
	}

	// Dashboard ---------------------------------------------------------------		
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;
		result = new ModelAndView("admin/dashboard");

		//The table with all the avgs and the stdevs
		List<List<Double>> list = new ArrayList<List<Double>>();
		list.add(Arrays.asList(rendezvousService.getRendezvousStats()));
		list.add(Arrays.asList(rendezvousService.getRendezvousUserStats()));
		list.add(Arrays.asList(rendezvousService.getUserRendezvousesStats()));
		list.add(Arrays.asList(rendezvousService.getRendezvousAnnouncementStats()));
		list.add(Arrays.asList(rendezvousService.getRendezvousQuestionStats()));
		list.add(Arrays.asList(rendezvousService.getAnswersToQuestionsStats()));
		list.add(Arrays.asList(commentService.getCommentRepliesStats()));
		result.addObject("list", list);
		
		//Another table to show ratio of users have ever created a rendezvous versus the users 
		//who have never created any rendezvouses
		result.addObject("ratioOfUsersWhoCreatedRendezvouses", rendezvousService.getRatioOfUsersWhoHaveCreatedRendezvouses());
		
		//Another table with the top 10 rendezvouses by RSVPs
		List<Rendezvous> top10Rendezvouses = new ArrayList<Rendezvous>();
		top10Rendezvouses.addAll(rendezvousService.getTop10RendezvousByRSVPs());
		result.addObject("top10RnedezVouses",top10Rendezvouses);
		
		//Another table to show the rendezvouses with the number of announcements over
		//the 75% avg
		List<Rendezvous> rendezvousWithAnnouncementsOverAvg = new ArrayList<Rendezvous>();
		rendezvousWithAnnouncementsOverAvg.addAll(rendezvousService.getRendezvousesWithNumberOfAnnouncementsOver75PerCentAvg());
		result.addObject("rendezvousWithAnnouncementsOverAvg", rendezvousWithAnnouncementsOverAvg);

		//Another table to show the rendezvouses linked to a number of rendezvouses that 
		//is greater than the average plus 10%.
		List<Rendezvous> rendezvousesLinkedToMoreThan110PerCent = new ArrayList<Rendezvous>();
		rendezvousesLinkedToMoreThan110PerCent.addAll(rendezvousService.getRendezvousesLinkedToMoreThan10PerCentAVGNumberOfRendezvouses());
		result.addObject("rendezvousesLinkedToMoreThan110PerCent", rendezvousService.getRendezvousesLinkedToMoreThan10PerCentAVGNumberOfRendezvouses());



		return result;
	}


}
