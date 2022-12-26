package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.repository.CategoryRepository;

/**
 * category取得に関与するservice.
 * 
 * @author shibatamasayuki
 *
 */
@Service
@Transactional
public class ShowCategoryService {

	@Autowired
	CategoryRepository repository;

	/**
	 * 大カテゴリーを全件取得.
	 * 
	 * @return categoryのList
	 */
	public List<Category> findAllBigCategory() {
		return repository.findAll();
	}

	/**
	 * 中カテゴリーを全件取得.
	 * 
	 * @return 中カテゴリーのList
	 */
	public List<Category> findMediumCategory(Integer id) {
		return repository.findMediumCategoryById(id);
	}

	/**
	 * 小カテゴリーを全件取得.
	 * 
	 * @return 小カテゴリーのList
	 */
	public List<Category> findSmallCategory(Integer id) {
		return repository.findSmallCategoryById(id);
	}

}
