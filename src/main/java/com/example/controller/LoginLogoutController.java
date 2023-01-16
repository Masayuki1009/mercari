package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.UserForm;
import com.example.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログイン、ログアウト機能に関するController.
 * 
 * @author shibatamasayuki
 *
 */
@RequestMapping("/login")
@Controller
public class LoginLogoutController {
	@Autowired
	LoginService loginService;
	
	@Autowired
	private HttpSession session;

	@GetMapping("")
	public String index(UserForm form, Model model) {
		return "login";
	}
	
	/**
	 * ログイン処理を行い、商品一覧画面に遷移.
	 * 
	 * @param form form
	 * @param result result
	 * @param model model
	 * @param response response
	 * @return 商品一覧画面
	 */
	@PostMapping("/login")
	public String login(@Validated UserForm form, BindingResult result, Model model, HttpServletResponse response) {
		if(result.hasErrors()) {
			return index(form, model);
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		System.out.println("user : " + user);
		User loginUser = loginService.login(user);
		if(loginUser == null) {
			model.addAttribute("loginError", "メールアドレス、またはパスワードが間違っています");
			return "/login";
		}
		session.setAttribute("loginUser", loginUser);
		return "redirect:/";
	}
	
	/**
	 * ログアウト処理.
	 * 
	 * @return ログイン画面
	 */
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
}
