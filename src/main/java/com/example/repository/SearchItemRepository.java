package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Items;

/**
 * itemsテーブルの検索を行う際に使用するrepository.
 * 
 * @author shibatamasayuki
 *
 */
@Repository
public class SearchItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Items> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(Items.class);
	
	/**
	 * 検索結果に応じたitemの取得を行う.
	 * 
	 * @param sql sql
	 * @return 検索結果に一致するitemリスト
	 */
	public List<Items> findAll(String sql) {
		
		List<Items> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
}
