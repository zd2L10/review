package com.example.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.domain.User;
import com.example.app.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userservice;

	@Autowired
	HttpSession session;

	@GetMapping("/home")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "home";
	}

	@PostMapping("/home")
	public String login(@Valid User user, Errors errors, HttpSession session) throws Exception {

		//
		if ((!user.getLoginId().isBlank() && !user.getLoginPass().isBlank())
				&& (!userservice.isCorrectIdAndPass(user.getLoginId(), user.getLoginPass()))) {
			errors.rejectValue("login", "incorrect_id_password");
		}
		// 入力不備確認のバリデーション
		if (errors.hasErrors()) {
			return "home";
		}

		session.setAttribute("user", userservice.getUserById(user.getLoginId()));
		return "redirect:/review";
	}
	
	@GetMapping("/add")
	public String addGet (Model model) throws Exception {
		model.addAttribute("user", new User());
		return "/add";
	}
	
	@PostMapping("/add")
	public String addPost(
			@Valid User user,
			Errors errors,
			Model model) throws Exception{
		User tempUser = null;
		tempUser = userservice.getUserById(user.getLoginId());
		if(tempUser != null) {
			errors.rejectValue("loginId", "login_id.in.use");
		}
		if(!user.getConfpass().equals(user.getLoginPass())) {
			errors.rejectValue("confpass", "confpass.not.same");
		}
		if(errors.hasErrors()) {
			return "/add";
		}
		user.setLoginPass(BCrypt.hashpw(user.getLoginPass(), BCrypt.gensalt()));
		model.addAttribute("user", user);
		userservice.addUser(user);
		return "redirect:/home";
	}
}
