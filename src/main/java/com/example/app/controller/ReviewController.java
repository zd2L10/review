package com.example.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Review;
import com.example.app.domain.User;
import com.example.app.service.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	ReviewService Rservice;

	@Autowired
	HttpSession session;

	private static final int NUM_PER_PAGE = 5;

	@GetMapping
	public String list(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) throws Exception {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("reviewList", Rservice.getReviewListByPage(page, NUM_PER_PAGE));
		model.addAttribute("page", page);
		model.addAttribute("totalPage", Rservice.getTotalPages(NUM_PER_PAGE));
		return "review/list";
	}

	@GetMapping("/add")
	public String addGet(Model model) throws Exception {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("title", "レビュー投稿");
		Review review = new Review();
		review.setName(user.getName());
		model.addAttribute("review", review);
		return "review/save";
	}

	@PostMapping("/add")
	public String addPost(HttpSession session, @Valid Review review, Errors errors, Model model) throws Exception {
		User user = (User) session.getAttribute("user");
		// 入力不備
		if (errors.hasErrors()) {
			
			model.addAttribute("user", user);
			model.addAttribute("title", "レビュー投稿");
			return "review/save";
		}
		
		review.setName(user.getName());
		Rservice.addReview(review);
		model.addAttribute("user", user);
		return "result/list";
	}
}
