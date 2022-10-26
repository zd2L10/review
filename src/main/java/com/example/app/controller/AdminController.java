package com.example.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Review;
import com.example.app.domain.User;
import com.example.app.service.ReviewService;
import com.example.app.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserService userservice;

	@Autowired
	ReviewService Rservice;

	@Autowired
	HttpSession session;

	private static final int NUM_PER_PAGE = 5;

	@GetMapping
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "/admin/login";
	}

	@PostMapping
	public String login(@Valid User user, Errors errors, HttpSession session) throws Exception {

		// 管理者権限の確認
		User admin = userservice.getUserById(user.getLoginId());
		if((!user.getLoginId().isBlank()) && (!admin.getRole().equals("admin"))) {
			errors.rejectValue("admin", "not_admin");
		}
		
		// IDとパスワードの正誤確認
		if ((!user.getLoginId().isBlank() && !user.getLoginPass().isBlank())
				&& (!userservice.isCorrectIdAndPass(user.getLoginId(), user.getLoginPass()))) {
			errors.rejectValue("login", "incorrect_id_password");
		}
		// 入力不備確認のバリデーション
		if (errors.hasErrors()) {
			return "/admin/login";
		}

		session.setAttribute("user", userservice.getUserById(user.getLoginId()));
		return "redirect:/admin/list";
	}
	
	@GetMapping("/list")
	public String list(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) throws Exception {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("reviewList", Rservice.getReviewListByPage(page, NUM_PER_PAGE));
		model.addAttribute("page", page);
		model.addAttribute("totalPage", Rservice.getTotalPages(NUM_PER_PAGE));
		return "/admin/adlist";
	}
	
	@GetMapping("/fix/{id}")
	public String fixGet(@PathVariable Integer id, Model model)throws Exception{
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("title", "レビュー編集");
		model.addAttribute("review", Rservice.getReviewById(id));
		return "/admin/adsave"; 
	}
	
	@PostMapping("/fix/{id}")
	public String addfix(HttpSession session, @Valid Review review, Errors errors, RedirectAttributes rd, Model model) throws Exception {
		User user = (User) session.getAttribute("user");
		// 入力不備
		if (errors.hasErrors()) {
			
			model.addAttribute("user", user);
			model.addAttribute("title", "レビュー編集");
			return "/admin/adsave";
		}
		
		Rservice.editReview(review);
		model.addAttribute("user", user);
		rd.addFlashAttribute("statusMessage", "レビューを修正しました。");
		return "redirect:/admin/list";
	}
}
