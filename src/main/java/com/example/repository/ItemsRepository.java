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
 * itemsテーブルへのDB操作を行うrepository.
 * 
 * @author shibatamasayuki
 *
 */
@Repository
public class ItemsRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Items> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(Items.class);

	/**
	 * 商品を30件分取得する.(ページング対策のため)
	 * 
	 * @return 商品30件(LIMIT 30)
	 */
	public List<Items> findAll() {
		String sql = "SELECT i.id, i.name, i.condition, c.name_all AS category, i.brand, i.price, i.shipping, i.description "
				+ "FROM items AS i INNER JOIN category AS c ON i.category = c.id ORDER BY id LIMIT 30;";
		List<Items> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 
	 * 引数のidと一致する商品情報を1件取得する.
	 * 
	 * @param id itemのid
	 * @return idと一致した商品情報
	 */
	public Items load(Integer id) {
		String sql = "SELECT i.id, i.name, i.condition, c.name_all AS category, i.brand, i.price, i.shipping, i.description FROM items AS i INNER JOIN category AS c ON i.category = c.id WHERE i.id = :i.id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("i.id", id);
		Items item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}

}
