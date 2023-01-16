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
import com.example.service.RegisterService;

/**
 * userテーブルを操作するController.
 * 
 * @author shibatamasayuki
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	RegisterService registerService;

	/**
	 * ユーザー登録画面に遷移.
	 * 
	 * @param form form
	 * @param model model
	 * @return ユーザー登録画面
	 */
	@GetMapping("")
	public String index(UserForm form, Model model) {
		return "register";
	}
	
	/**
	 * 新しくユーザーを登録し、ログイン画面に遷移.
	 * 
	 * @param form form
	 * @param result result
	 * @param model model
	 * @return ログイン画面
	 */
	@PostMapping("/registration")
	public String registration(@Validated UserForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return index(form, model);
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		//決め打ち
		user.setAuthority(0);
		registerService.register(user);
		return "redirect:/login";
	}
}
