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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Category;
import com.example.domain.NewItem;
import com.example.form.ItemForm;
import com.example.service.AddNewItemService;
import com.example.service.ShowCategoryService;

/**
 * itemテーブルを操作するController.
 * 
 * @author shibatamasayuki
 *
 */
@Controller
@RequestMapping("/add")
public class AddNewItemController {

	@Autowired
	ShowCategoryService showCategoryService;

	@Autowired
	AddNewItemService addNewItemService;

	/**
	 * item追加画面に遷移する.
	 * 
	 * @param model model
	 * @param form  form
	 * @return item追加画面
	 */
	@GetMapping("")
	public String index(Model model, ItemForm form) {
		List<Category> bigCategoryList = showCategoryService.findAllBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);
		// item追加成功時(submitメソッドより)にflash scopeに追加しているメッセージを取得.
		String confirmedMessage = (String) model.getAttribute("confirmed");
		model.addAttribute("confirmed", confirmedMessage);
		return "add";
	}

	/**
	 * itemの追加を行う.
	 * 
	 * @param form               form
	 * @param result             result
	 * @param model              model
	 * @param redirectAttributes redirectAttributes(flash scopeを使用)
	 * @return item追加画面(redirect)
	 */
	@PostMapping("/submit")
	public String submit(@Validated ItemForm form, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			// categoryフィールドにてエラーが発生しているかどうかを確認する
			boolean isCatgeoryErrored = result.hasFieldErrors("category");
			// エラーが発生している場合、エラーメッセージを表示
			if (isCatgeoryErrored == true) {
				model.addAttribute("categoryError", "categoryを選択して下さい");
			}
			return index(model, form);
		}
		NewItem newItem = new NewItem();
		newItem.setPrice(Double.parseDouble(form.getPrice()));
		// 決め打ち
		newItem.setShipping(0);
		BeanUtils.copyProperties(form, newItem);
		addNewItemService.addNewItem(newItem);
		// item追加完了時、追加完了通知をブラウザに表示させるために、flash scopeを使用
		// flash scope : redirect先画面にパラメータ(今回の場合confirmed)を渡せる
		redirectAttributes.addFlashAttribute("confirmed", newItem.getName() + "が新しく在庫に追加されました!");
		return "redirect:/add";
	}

	/**
	 * big categoryを選んだ際、そのidと紐づいているmedium categoryを取得し、changeSelextbox.jsに値を返す.
	 * 
	 * @param model model
	 * @param id    大カテゴリーのid
	 * @return 大カテゴリーと紐づいている、中カテゴリーのリスト(changeSelextbox.jsに成功結果を返す)
	 */
	@ResponseBody
	@PostMapping("/medium")
	public List<Category> medium(Model model, Integer id) {
		List<Category> mediumCategoryList = showCategoryService.findMediumCategory(id);
		model.addAttribute("mediumCategoryList", mediumCategoryList);
		return mediumCategoryList;
	}

	/**
	 * medium categoryを選んだ際、そのidと紐づいているsmall categoryを取得し、changeSelextbox.jsに値を返す.
	 * 
	 * @param model model
	 * @param id    中カテゴリーのid
	 * @return 中カテゴリーと紐づいている、小カテゴリーのリスト(changeSelextbox.jsに成功結果を返す)
	 */
	@ResponseBody
	@PostMapping("/small")
	public List<Category> small(Model model, Integer id) {
		List<Category> smallCategoryList = showCategoryService.findSmallCategory(id);
		return smallCategoryList;
	}

}
