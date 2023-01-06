package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.repository.CategoryRepository;

/**
 * categoryテーブルの操作に関与するservice.
 * 
 * @author shibatamasayuki
 *
 */
@Service
@Transactional
public class FindCategoryIdService {

	@Autowired
	CategoryRepository repository;

	/**
	 * カテゴリー名と一致する大カテゴリーのidを取得する.
	 * 
	 * @param bigCategoryName 大カテゴリー名
	 * @return 大カテゴリーid
	 */
	public Integer findIdByBigCategoryName(String bigCategoryName) {
		Integer bigCategoryId = repository.findBigCategoryIdByName(bigCategoryName).getId();
		return bigCategoryId;
	}
	
	/**
	 * 引数の大カテゴリーのidをparentカラムに持ち、かつ引数の中カテゴリー名と一致する中カテゴリーの情報を取得する.
	 * 
	 * @param bigCategoryName 大カテゴリー名
	 * @return 大カテゴリーid
	 */
	public Integer findMediumCategoryIdByName(Integer bigCategoryId, String mediumCategoryName) {
		Integer mediumCategoryId = repository.findMediumCategoryIdByName(bigCategoryId, mediumCategoryName).getId();
		return mediumCategoryId;
	}
}
