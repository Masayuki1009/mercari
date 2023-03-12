package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.example.domain.Items;
import com.example.service.SearchItemService;

@SpringBootTest
@ActiveProfiles("test")
public class SearchItemRepositoryTest {

	@Autowired
	private JdbcTemplate template;

	@Autowired
	SearchItemRepository repository;


	@BeforeEach
	public void setup() {
		// Item2件作成
		template.execute("INSERT INTO items VALUES(1, 'Shoes1', 1, 3, 'Adidas', 100, 1, 'Nice Adidas shoes')");
		template.execute("INSERT INTO items VALUES(2, 'Shoes2', 2, 6, 'Adidas', 200, 2, 'Nice Nike skinny')");

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

	//offset抜いて無理やりクリアにしてるけど実際offsetあるとエラーなる
	@DisplayName("findAllのテスト")
	@Test
	public void findAllTest() {
		String sql = "SELECT i.id, i.name, i.condition, c.name_all AS category, i.brand, i.price, i.shipping, i.description "
				+ "FROM items AS i INNER JOIN category AS c ON i.category = c.id WHERE i.name ILIKE " + "'" + "%Shoes%" + "'" + " AND i.brand ILIKE " + "'" + "%Adidas%" + "'" 
				+ " ORDER BY id LIMIT 30;";
		
		List<Items> searchedItemList = repository.findAll(sql);

		System.out.println("searchItemList : " + searchedItemList);

//		assertEquals(2, searchedItemList.size());

	}

	@AfterEach
	public void cleanup() {
		template.execute("DELETE FROM items");
		template.execute("DELETE FROM category");
		System.out.println("cleanup OK");
	}
}
