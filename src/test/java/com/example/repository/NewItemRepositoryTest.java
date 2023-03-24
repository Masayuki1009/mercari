package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.example.domain.Items;
import com.example.domain.Items;
import com.example.domain.NewItem;

@SpringBootTest
@ActiveProfiles("test")
public class NewItemRepositoryTest {

	@Autowired
	private JdbcTemplate template;

	@Autowired
	NewItemRepository repository;

	@Autowired
	ItemsRepository itemsRepository;

	@BeforeEach
	public void setup() {
		// Item2件作成
//		template.execute("INSERT INTO items VALUES(1, 'Shoes', 1, 3, 'Adidas', 100, 1, 'Nice Adidas shoes')");
//		template.execute("INSERT INTO items VALUES(2, 'skinny', 2, 6, 'Nike', 200, 2, 'Nice Nike skinny')");

		// 大カテゴリー作成
		template.execute("INSERT INTO category VALUES(1, null, 'Men', null)");
		template.execute("INSERT INTO category VALUES(4, null, 'Women', null)");
		// 中カテゴリー作成
		template.execute("INSERT INTO category VALUES(2, 1, 'Clothes', null)");
		template.execute("INSERT INTO category VALUES(5, 4, 'Clothes', null)");
		// 小カテゴリー作成
		template.execute("INSERT INTO category VALUES(3, 2, 'Shoes', 'Men/Clothes/Shoes')");
		template.execute("INSERT INTO category VALUES(6, 5, 'Pants', 'Women/Clothes/Pants')");

		System.out.println("setup OK");
	}

	@DisplayName("insertテスト")
	@Test
	public void testInsert() {
		NewItem newItem = new NewItem();
		newItem.setId(1);
		newItem.setName("iPhone");
		newItem.setCondition(3);
		newItem.setCategory(3);
		newItem.setBrand("apple");
		newItem.setPrice(1000);
		newItem.setShipping(1);
		newItem.setDescription("new iPhone series");

		repository.insert(newItem);

		Items newItem1 = itemsRepository.load(1);
		System.out.println("newItem : " + newItem);

		assertEquals("iPhone", newItem1.getName());
	}

	@AfterEach
	public void cleanup() {
		template.execute("DELETE FROM items");
		template.execute("DELETE FROM category");
		System.out.println("cleanup OK");
	}
}
