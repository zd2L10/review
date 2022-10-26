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

import com.example.app.domain.User;
import com.example.app.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserService userservice;

	@Autowired
	HttpSession session;

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
		return "redirect:/admin/review";
	}
}
