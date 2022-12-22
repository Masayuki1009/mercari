package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Items;
import com.example.service.ShowItemsService;

/**
 * itemテーブルを操作するController.
 * 
 * @author shibatamasayuki
 *
 */
@Controller
@RequestMapping("/show")
public class ShowItemsController {

	@Autowired
	ShowItemsService showItemsService;

	/**
	 * 商品一覧画面を表示する.
	 * 
	 * @param model model
	 * @return 商品一覧画面
	 */
	@GetMapping("")
	public String showItemsList(Model model) {
		List<Items> itemList = showItemsService.showItemList();
		model.addAttribute("itemList", itemList);
		
		return "list";
	}
}
