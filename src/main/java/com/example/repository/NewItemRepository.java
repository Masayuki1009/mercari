package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.NewItem;

/**
 * itemsテーブルへのテーブル操作を行うrepository.
 * 
 * @author shibatamasayuki
 *
 */
@Repository
public class NewItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * itemsの追加を行う.
	 * 
	 * @param items items
	 */
	public void insert(NewItem newItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(newItem);
		String sql = "Insert into items(name, condition, category, brand, price, shipping, description) values(:name, :condition, :category, :brand, :price, :shipping, :description);";
		template.update(sql, param);
	}
}
