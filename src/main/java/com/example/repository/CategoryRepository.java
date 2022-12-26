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
}
