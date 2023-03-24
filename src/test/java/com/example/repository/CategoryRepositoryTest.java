package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.example.domain.Category;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryRepositoryTest {

	@Autowired
	private JdbcTemplate template;

	@Autowired
	CategoryRepository repository;

	@BeforeEach
	public void setup() {
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

	@DisplayName("findAllメソッドのテスト")
	@Test
	public void testFindAll() {
		List<Category> categoryList = repository.findAll();
		System.out.println("取得した大カテゴリーのリスト" + categoryList);
		assertEquals(2, categoryList.size());
	}

	@DisplayName("findMediumCategoryByIdメソッドのテスト")
	@Test
	public void testfindMediumCategoryById() {
		List<Category> mediumCategoryList = repository.findMediumCategoryById(4);
		System.out.println("取得した中カテゴリーのリスト" + mediumCategoryList);
		assertEquals(1, mediumCategoryList.size());
	}
	
	@DisplayName("findSmallCategoryByIdメソッドのテスト")
	@Test
	public void testfindSmallCategoryById() {
		List<Category> smallCategoryList = repository.findSmallCategoryById(5);
		System.out.println("取得した小カテゴリーのリスト" + smallCategoryList);
		assertEquals(1, smallCategoryList.size());
	}
	
	@DisplayName("findBigCategoryIdByNameメソッドのテスト")
	@Test
	public void testfindBigCategoryIdByName() {
		Category bigCategory = repository.findBigCategoryIdByName("Men");
		System.out.println("取得した大カテゴリ" + bigCategory);
		assertEquals("Men", bigCategory.getName());
	}
	
	@DisplayName("findMediumCategoryIdByNameメソッドのテスト")
	@Test
	public void testfindMediumCategoryIdByName() {
		Category mediumCategory = repository.findMediumCategoryIdByName(1, "Clothes");
		System.out.println("取得した中カテゴリ" + mediumCategory);
		assertEquals("Clothes", mediumCategory.getName());
	}

	@AfterEach
	public void cleanup() {
		template.execute("DELETE FROM category");
		System.out.println("cleanup OK");
	}
}
