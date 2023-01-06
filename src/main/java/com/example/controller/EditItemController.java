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
import com.example.domain.EditItem;
import com.example.domain.Items;
import com.example.form.EditItemForm;
import com.example.service.EditItemService;
import com.example.service.FindCategoryIdService;
import com.example.service.ShowCategoryService;
import com.example.service.ShowItemDetailService;

/**
 * itemテーブルを操作するコントローラー.
 * 
 * @author shibatamasayuki
 *
 */
@Controller
@RequestMapping("/edit")
public class EditItemController {
	@Autowired
	ShowItemDetailService showItemDetailService;

	@Autowired
	ShowCategoryService showCategoryService;

	@Autowired
	FindCategoryIdService findCategoryIdService;

	@Autowired
	EditItemService editItemService;

	/**
	 * 編集画面へ遷移. 遷移後に、選択した商品のカテゴリーが自動で編集画面のデフォルトに反映されるようにしておく.
	 * 
	 * @param id    id
	 * @param model model
	 * @return 編集画面
	 */
	@GetMapping("")
	public String index(Integer id, Model model) {
		Items item = showItemDetailService.showItemDetail(id);
		model.addAttribute("item", item);
		String[] category = item.getCategory().split("/", 0);
		// 編集ページ遷移時の商品の大カテゴリー(th:selectedで初期値として設定)
		model.addAttribute("bigCategory", category[0]);

		List<Category> bigCategoryList = showCategoryService.findAllBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);

		Integer bigCategoryId = findCategoryIdService.findIdByBigCategoryName(category[0]);
		List<Category> mediumCategoryList = showCategoryService.findMediumCategory(bigCategoryId);
		// 初期値の大カテゴリーと紐づく中カテゴリーのリスト
		model.addAttribute("mediumCategoryList", mediumCategoryList);
		// 編集ページ遷移時の商品の中カテゴリー(th:selectedで初期値として設定)
		model.addAttribute("mediumCategory", category[1]);

		Integer mediumCategoryId = findCategoryIdService.findMediumCategoryIdByName(bigCategoryId, category[1]);
		List<Category> smallCategoryList = showCategoryService.findSmallCategory(mediumCategoryId);
		// 初期値の中カテゴリーと紐づく中カテゴリーのリスト
		model.addAttribute("smallCategoryList", smallCategoryList);
		// 編集ページ遷移時の商品の小カテゴリー(th:selectedで初期値として設定)
		model.addAttribute("smallCategory", category[2]);

		return "edit";
	}

	/**
	 * 編集後、商品一覧画面へ遷移する.
	 * 
	 * @param form form
	 * @param result result
	 * @param model model 
	 * @param id id
	 * @return 商品一覧ページ
	 */
	@PostMapping("/submit")
	public String submit(@Validated EditItemForm form, BindingResult result, Model model, Integer id) {
		if (result.hasErrors()) {
			// 各項目にてエラーが発生しているかどうかを確認する
			boolean isNameErrored = result.hasFieldErrors("name");
			boolean isCatgeoryErrored = result.hasFieldErrors("category");
			boolean isPriceErrored = result.hasFieldErrors("price");
			boolean isDescriptionErrored = result.hasFieldErrors("description");
			// エラーが発生している場合、エラーメッセージを表示
			if (isNameErrored == true) {
				model.addAttribute("nameError", "nameの選択は必須です");
			}
			if (isCatgeoryErrored == true) {
				model.addAttribute("categoryError", "categoryの選択は必須です");
			}
			if (isPriceErrored == true) {
				model.addAttribute("priceError", "priceの選択は必須です(priceは1ドル以上から選択してください)");
			}
			if (isDescriptionErrored == true) {
				model.addAttribute("descriptionError", "descriptionの選択は必須です");
			}
			return index(id, model);
		}
		Items item = showItemDetailService.showItemDetail(id);
		EditItem edittedItem = new EditItem();
		BeanUtils.copyProperties(form, edittedItem);
		edittedItem.setShipping(item.getShipping());
		editItemService.editItem(edittedItem);

		return "redirect:/show";
	}
}
