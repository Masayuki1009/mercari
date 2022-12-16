package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller("/test")
public class testController {

	@GetMapping("")
	public String index() {
		return "index";
	}
}
