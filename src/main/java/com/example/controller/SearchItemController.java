package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Items;
import com.example.domain.SearchItem;
import com.example.form.SearchForm;
import com.example.service.SearchItemService;
import com.example.service.ShowCategoryService;
import com.example.service.ShowItemsService;

@RequestMapping("/search")
@Controller
public class SearchItemController {

	@Autowired
	SearchItemService searchItemService;

	@Autowired
	ShowItemsService showItemsService;

	@Autowired
	ShowCategoryService showCategoryService;
	
	@Autowired
	ShowItemsController showItemsController;

	@PostMapping("")
	public String search(@Validated SearchForm form, BindingResult result,  Model model, Integer currentPage) {
		System.out.println("current page : " + currentPage);
		if (result.hasErrors()) {
			// 各項目にてエラーが発生しているかどうかを確認する
			boolean isCatgeoryErrored = result.hasFieldErrors("category");
			// エラーが発生している場合、エラーメッセージを表示
			if (isCatgeoryErrored == true) {
				model.addAttribute("categoryError", "categoryは小カテゴリーまで選択してください");
			}
			System.out.println("searchItem error");
			System.out.println(currentPage);
			return showItemsController.showItemsList(model, currentPage);
		}
		
		List<Category> bigCategoryList = showCategoryService.findAllBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);
		System.out.println("searchItemCont form内容 : " + form);
		Integer maxPage = showItemsService.getItemsCount() / 30 + 1;
		currentPage = 1;
		System.out.println(currentPage);
		SearchItem searchItem = new SearchItem();
		BeanUtils.copyProperties(form, searchItem);
		List<Items> searchItemList = searchItemService.searchItem(searchItem);
		model.addAttribute("itemList", searchItemList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("maxPage", maxPage);
		return "list";
	}

}
