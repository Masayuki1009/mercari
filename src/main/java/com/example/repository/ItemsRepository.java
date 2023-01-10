package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Count;
import com.example.domain.EditItem;
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
	private static final RowMapper<Count> COUNT_ROW_MAPPER = new BeanPropertyRowMapper<>(Count.class);

	/**
	 * 特定の箇所(offsetPoint)から、商品を30件分取得する.
	 * 
	 * @return 商品30件(LIMIT 30)
	 */
	public List<Items> findAll(Integer offsetPoint) {
		String sql = "SELECT i.id, i.name, i.condition, c.name_all AS category, i.brand, i.price, i.shipping, i.description "
				+ "FROM items AS i INNER JOIN category AS c ON i.category = c.id ORDER BY id OFFSET :offset LIMIT 30;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("offset", offsetPoint);
		List<Items> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
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

	/**
	 * idが一致するレコードを引数のeditItemの内容に更新する.
	 * 
	 * @param editItem editItem
	 */
	public void update(EditItem editItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(editItem);
		String sql = "UPDATE items SET name = :name, condition = :condition, category = :category, brand = :brand, price = :price, shipping = :shipping, description = :description WHERE id = :id;";
		template.update(sql, param);
	}

	/**
	 * itemsテーブルの商品数を取得する.
	 * 
	 * @return itemsテーブルの商品数の合計
	 */
	public Integer findItemsCount() {
		String sql = "SELECT count(*) FROM items;";
		List<Count> itemsCount = template.query(sql, COUNT_ROW_MAPPER);
		Integer count = itemsCount.get(0).getCount();
		return count;
	}

}
