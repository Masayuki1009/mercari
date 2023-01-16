package com.example.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Items;
import com.example.form.SearchForm;
import com.example.service.ShowCategoryService;
import com.example.service.ShowItemDetailService;
import com.example.service.ShowItemsService;

/**
 * itemテーブルを操作するController.
 * 
 * @author shibatamasayuki
 *
 */
@Controller
@RequestMapping("")
public class ShowItemsController {

	@Autowired
	ShowItemsService showItemsService;

	@Autowired
	ShowItemDetailService showItemDetailService;
	
	@Autowired
	ShowCategoryService showCategoryService;

	/**
	 * 商品一覧画面へ遷移する.
	 * 
	 * @param model model
	 * @return 商品一覧画面
	 */
	@GetMapping("/")
	public String showItemsList(Model model, Integer currentPage) {
		List<Category> bigCategoryList = showCategoryService.findAllBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);
		
		Integer maxPage = showItemsService.getItemsCount() / 30 + 1;
		if(currentPage == null || currentPage <= 0) {
			currentPage = 1;
		}
		Integer offsetPoint = (currentPage - 1) * 30;
		List<Items> itemList = showItemsService.showItemList(offsetPoint);
		model.addAttribute("itemList", itemList);
		model.addAttribute("currentPage", currentPage);	
		model.addAttribute("maxPage", maxPage);	
		return "list";
	}
	
	/**
	 * 特定のページ数を選択しGoボタンが押された時、そのページ番号のitemsを取得し、商品一覧ページへ遷移する.
	 * 
	 * @param model model
	 * @param designatedPage 指定されたpage
	 * @return 商品一覧ページ
	 */
	@PostMapping("/designatedPage")
	public String designatedPage(Model model, Integer designatedPage) {
		Integer maxPage = showItemsService.getItemsCount() / 30 + 1;
		if(designatedPage == null || designatedPage <= 0) {
			designatedPage = 1;
		}
		Integer offsetPoint = (designatedPage - 1) * 30;
		List<Items> itemList = showItemsService.showItemList(offsetPoint);
		model.addAttribute("itemList", itemList);	
		model.addAttribute("currentPage", designatedPage);	
		model.addAttribute("maxPage", maxPage);	
		return "list";
	}

	/**
	 * 詳細表示画面へ遷移する.
	 * 
	 * @param id    itemのid
	 * @param model model
	 * @return 詳細表示画面
	 */
	@GetMapping("/detail/{id}/{currentPage}")
	public String showDetail(@PathVariable("id") Integer id, @PathVariable("currentPage") Integer currentPage, Model model) {
		Items item = showItemDetailService.showItemDetail(id);
		model.addAttribute("item", item);
		model.addAttribute("currentPage", currentPage);
		return "detail";
	}
	
}
