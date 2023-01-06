package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;

/**
 * category tableの操作を行うrepository.
 * 
 * 
 * @author shibatamasayuki
 *
 */
@Repository
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = new BeanPropertyRowMapper<>(Category.class);
	
	/**
	 * 大カテゴリーを全件取得する.
	 * 
	 * @return 大カテゴリー
	 */
	public List<Category> findAll() {
		String sql = "SELECT id, parent, name, name_all FROM category WHERE parent IS NULL AND name_all IS NULL ORDER BY id;";
		List<Category> categoryList = template.query(sql, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 大カテゴリーのidと紐づいた中カテゴリーを全件取得する.
	 * 
	 * @return カテゴリーのList
	 */
	public List<Category> findMediumCategoryById(Integer id) {
		String sql = "SELECT id, parent, name, name_all FROM category WHERE parent = :id AND name_all IS NULL ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 中カテゴリーのidと紐づいた小カテゴリーを全件取得する.
	 * 
	 * @return カテゴリーのList
	 */
	public List<Category> findSmallCategoryById(Integer id) {
		String sql = "SELECT id, parent, name, name_all FROM category WHERE parent = :id ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 大カテゴリー名と一致する大カテゴリーの情報を取得する.
	 * 
	 * @param bigCategoryName 大カテゴリー名
	 * @return 大カテゴリー
	 */
	public Category findBigCategoryIdByName(String bigCategoryName) {
		String sql = "SELECT id, parent, name, name_all FROM category WHERE name = :name AND parent IS NULL AND name_all IS NULL;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", bigCategoryName);
		Category bigCategory = template.queryForObject(sql, param, CATEGORY_ROW_MAPPER);
		return bigCategory;
	}
	
	/**
	 * 引数の大カテゴリーのidをparentカラムに持ち、かつ引数の中カテゴリー名と一致する中カテゴリーの情報を取得する.
	 * 
	 * @param bigCategoryId 大カテゴリーid
	 * @param mediumCategoryName 中カテゴリーの名前
	 * @return 中カテゴリー
	 */
	public Category findMediumCategoryIdByName(Integer bigCategoryId, String mediumCategoryName) {
		String sql = "SELECT id, parent, name, name_all FROM category WHERE name = :name AND parent = :parent AND name_all IS NULL;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", mediumCategoryName).addValue("parent", bigCategoryId);
		Category mediumCategory = template.queryForObject(sql, param, CATEGORY_ROW_MAPPER);
		return mediumCategory;
	}
}
