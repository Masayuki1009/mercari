package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Items;
import com.example.domain.SearchItem;
import com.example.form.SearchForm;
import com.example.service.SearchItemService;
import com.example.service.ShowCategoryService;
import com.example.service.ShowItemsService;

/**
 * itemテーブルを操作するコントローラー.
 * itemの検索機能に関わる部分.
 * 
 * @author shibatamasayuki
 *
 */
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
	
	//直近の検索履歴を保持する変数、初期値はnull
	private SearchForm currentSearchLog = null;

	/**
	 * 検索を行い、検索結果に該当するitemのみをまとめた検索ページに遷移.
	 * 
	 * @param form form
	 * @param result result
	 * @param model model
	 * @param currentPage 現時点のページ番号
	 * @return 検索ページ
	 */
	@PostMapping("")
	public String search(@Validated SearchForm form, BindingResult result,  Model model, Integer currentPage) {
		if (result.hasErrors()) {
			// 各項目にてエラーが発生しているかどうかを確認する
			boolean isCatgeoryErrored = result.hasFieldErrors("category");
			// エラーが発生している場合、エラーメッセージを表示
			if (isCatgeoryErrored == true) {
				model.addAttribute("categoryError", "categoryは小カテゴリーまで選択してください");
			}
			return showItemsController.showItemsList(model, currentPage);
		}
		
		List<Category> bigCategoryList = showCategoryService.findAllBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);
		//検索結果(=form)をcurrentSearchLogに持たせる(検索後のページでページ移動を行う(turnPageメソッド)際に必要)
		currentSearchLog = form;
		
		Integer maxPage = showItemsService.getItemsCount() / 30 + 1;
		// 検索成功しているのでpageは1ページ目、また1ページ目を表示するのでoffsetも0
		currentPage = 1;
		Integer offsetPoint = 0;
		
		SearchItem searchItem = new SearchItem();
		BeanUtils.copyProperties(form, searchItem);
		List<Items> searchItemList = searchItemService.searchItem(offsetPoint, searchItem);
		model.addAttribute("itemList", searchItemList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("maxPage", maxPage);
		return "search";
	}
	
	/**
	 * (検索ページにて)検索ページ内のページングを行う.
	 * 
	 * @param model model
	 * @param searchItemForm searchItemForm_
	 * @param currentPage currentPage
	 * @return 検索ページ
	 */
	@GetMapping("/turnPage")
	public String turnPage(Model model, SearchForm searchItemForm, Integer currentPage) {	
		System.out.println("turnPage");
		List<Category> bigCategoryList = showCategoryService.findAllBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);	
		Integer maxPage = showItemsService.getItemsCount() / 30 + 1;
		if(currentPage == null || currentPage <= 0) {
			currentPage = 1;
		}
		Integer offsetPoint = (currentPage - 1) * 30;
		
		SearchItem searchItem = new SearchItem();
		BeanUtils.copyProperties(currentSearchLog, searchItem);
		List<Items> itemList = searchItemService.searchItem(offsetPoint, searchItem);
		
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("itemList", itemList);
		return "search";
	}
	
	/**
	 * 特定のページ数を選択しGoボタンが押された時、そのページ番号のitemsを取得し、商品一覧ページへ遷移する.
	 * 
	 * @param model model
	 * @param designatedPage (商品一覧画面で)入力されたページ
	 * @return 検索ページ
	 */
	@PostMapping("/designatedPage")
	public String designatedPage(Model model, Integer designatedPage) {
		Integer maxPage = showItemsService.getItemsCount() / 30 + 1;
		if(designatedPage == null || designatedPage <= 0) {
			designatedPage = 1;
		}
		SearchItem searchItem = new SearchItem();
		BeanUtils.copyProperties(currentSearchLog, searchItem);
		Integer offsetPoint = (designatedPage - 1) * 30;
		List<Items> itemList = searchItemService.searchItem(offsetPoint, searchItem);
		model.addAttribute("itemList", itemList);
		model.addAttribute("currentPage", designatedPage);	
		model.addAttribute("maxPage", maxPage);	
		return "search";
	}

}
