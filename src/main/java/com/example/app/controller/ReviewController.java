package com.example.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.User;
import com.example.app.service.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	ReviewService Rservice;
	
	@Autowired
	HttpSession session;
	
	@GetMapping
	public String list(Model model) throws Exception{
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("review", Rservice.getReviewList());
		return "review/list";
	}
}
