package com.example.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Items;
import com.example.domain.SearchItem;
import com.example.repository.SearchItemRepository;

@SpringBootTest
public class SearchItemServiceTest {

	@Mock
	SearchItemRepository repository;

	@InjectMocks
	SearchItemService service;


	@DisplayName("全部ある場合の動作確認")
	@Test
	public void checkPatternOne() {
		SearchItem searchItem = new SearchItem();
		searchItem.setBrand("nike");
		searchItem.setCategory(3);
		searchItem.setName("shoes");
		
		Items item = new Items();
		item.setId(1);
		item.setName("shoes");
		item.setCondition(1);
		item.setCategory("Men/Clothe/Shoes");
		item.setBrand("nike");
		item.setPrice(100);
		item.setShipping(1);
		item.setDescription("good one");

		List<Items> itemList = new ArrayList<>();
		itemList.add(item);
		
		Integer offsetPoint = 30;
		
		String sql = "SELECT i.id, i.name, i.condition, c.name_all AS category, i.brand, i.price, i.shipping, i.description "
				+ "FROM items AS i INNER JOIN category AS c ON i.category = c.id WHERE i.name ILIKE " + "'" + "%" + searchItem.getName() + "%" + "'" + " AND i.brand ILIKE " + "'" + "%" + searchItem.getBrand() + "%" + "'" 
				+ "AND i.category = " + searchItem.getCategory() + " ORDER BY id OFFSET " + offsetPoint + " LIMIT 30;";

		//期待値
		when(repository.findAll(sql)).thenReturn(itemList);
		
		//execute
		service.searchItem(offsetPoint, searchItem);
		
		//assertion
		verify(repository).findAll(sql);

	}
	
	@DisplayName("categoryがない場合の動作確認")
	@Test
	public void checkPatternTwo() {
		SearchItem searchItem = new SearchItem();
		searchItem.setBrand("nike");
		searchItem.setCategory(0);
		searchItem.setName("shoes");
		
		Items item = new Items();
		item.setId(1);
		item.setName("shoes");
		item.setCondition(1);
		item.setCategory("Men/Clothe/Shoes");
		item.setBrand("nike");
		item.setPrice(100);
		item.setShipping(1);
		item.setDescription("good one");

		List<Items> itemList = new ArrayList<>();
		itemList.add(item);
		
		Integer offsetPoint = 30;
		
		String sql = "SELECT i.id, i.name, i.condition, c.name_all AS category, i.brand, i.price, i.shipping, i.description "
				+ "FROM items AS i INNER JOIN category AS c ON i.category = c.id WHERE i.name ILIKE " + "'" + "%" + searchItem.getName() + "%" + "'" + " AND i.brand ILIKE " + "'" + "%" + searchItem.getBrand() + "%" + "'" 
				+ " ORDER BY id OFFSET " + offsetPoint + " LIMIT 30;";

		//期待値
		when(repository.findAll(sql)).thenReturn(itemList);
		
		//execute
		service.searchItem(offsetPoint, searchItem);
		
		//assertion
		verify(repository).findAll(sql);

	}

}
