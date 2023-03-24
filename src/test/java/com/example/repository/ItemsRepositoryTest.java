package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.example.domain.EditItem;
import com.example.domain.Items;

@SpringBootTest
@ActiveProfiles("test")
public class ItemsRepositoryTest {

	@Autowired
	private JdbcTemplate template;

	@Autowired
	ItemsRepository repository;

	@BeforeEach
	public void setup() {
		// Item2件作成
		template.execute("INSERT INTO items VALUES(1, 'Shoes', 1, 3, 'Adidas', 100, 1, 'Nice Adidas shoes')");
		template.execute("INSERT INTO items VALUES(2, 'skinny', 2, 6, 'Nike', 200, 2, 'Nice Nike skinny')");

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
//
//	@DisplayName("findAllのテスト")
//	@Test
//	public void testFindAll() {
//		Integer offsetPoint = 0;
//		List<Items> itemList = repository.findAll(0);
//		System.out.println("list一覧 : " + itemList);
//		assertEquals(2, itemList.size());
//	}

	@DisplayName("loadのテスト")
	@Test
	public void testLoad() {

		Items item = repository.load(1);
		System.out.println("item一覧 : " + item);
		assertEquals("Shoes", item.getName());
	}

	@DisplayName("updateのテスト")
	@Test
	public void testUpdate() {
		// edititemを作成
		EditItem edititem = new EditItem();
		edititem.setId(1);
		edititem.setName("keyboard");
		edititem.setCondition(5);
		edititem.setCategory(6);
		edititem.setBrand("apple");
		edititem.setPrice(500);
		edititem.setShipping(5);
		edititem.setDescription("great apple keyboard");

		// edititemをassertion
		assertEquals("keyboard", edititem.getName());

		// editするitemを取得
		Items originalItem = repository.load(1);
		assertEquals("Shoes", originalItem.getName());

		// editする
		repository.update(edititem);

		// editされたitem取得
		Items changedItem = repository.load(1);
		System.out.println("編集されたitem内容 : " + changedItem);
		assertEquals("keyboard", changedItem.getName());

	}

//	@DisplayName("findItemsCountのテスト")
//	@Test
//	public void testfindItemsCount() {
//		List<Items> itemList = new ArrayList<>();
//		itemList.add(repository.load(1));
//		itemList.add(repository.load(2));
//		Integer count = repository.findItemsCount();
//		System.out.println("count : " + count);
//		assertEquals(2, count);
//	}

	@AfterEach
	public void cleanup() {
		template.execute("DELETE FROM items");
		template.execute("DELETE FROM category");
		System.out.println("cleanup OK");
	}

}
