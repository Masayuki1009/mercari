package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Items;
import com.example.domain.SearchItem;
import com.example.repository.SearchItemRepository;

@Transactional
@Service
public class SearchItemService {

	@Autowired
	SearchItemRepository repository;
	
	
	public List<Items> searchItem(SearchItem searchItem) {
		String sql = "SELECT i.id, i.name, i.condition, c.name_all AS category, i.brand, i.price, i.shipping, i.description "
				+ "FROM items AS i INNER JOIN category AS c ON i.category = c.id ";
		
		//全部ある場合
		if(searchItem.getCategory() != 0) {
			sql += "WHERE i.name ILIKE " + "'" + "%" + searchItem.getName() + "%" + "'" + " AND i.brand ILIKE " + "'" + "%" + searchItem.getBrand() + "%" + "'" 
		+ "AND i.category = " + searchItem.getCategory();
		}
		
		// categoryのみない場合
		if(searchItem.getCategory() == 0) {
			sql += "WHERE i.name ILIKE " + "'" + "%" + searchItem.getName() + "%" + "'" + " AND i.brand ILIKE " + "'" + "%" + searchItem.getBrand() + "%" + "'";
		}
		
		sql += " ORDER BY id LIMIT 30;";
		
		return repository.findAll(sql);
	}
}
