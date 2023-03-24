package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Category;
import com.example.repository.CategoryRepository;

@SpringBootTest
public class FindCategoryIdServiceTest {
	
	@Mock
	CategoryRepository repository;

	@InjectMocks
	FindCategoryIdService service;
	
	@DisplayName("findIdByBigCategoryNameメソッドの動作確認")
	@Test
	public void testFindIdByBigCategoryName() {
		// モックの戻り値を設定
		String bigCategoryName = "Men";
		Category category = new Category();
		category.setId(1);
		when(repository.findBigCategoryIdByName(bigCategoryName)).thenReturn(category);

		// テスト実行
		Integer bigCategoryId = service.findIdByBigCategoryName(bigCategoryName);
		

		// 結果の検証
		assertEquals(1, bigCategoryId);
	}

	@DisplayName("findMediumCategoryIdByNameメソッドの動作確認")
	@Test
	public void testFindMediumCategoryIdByName() {
		// モックの戻り値を設定
		Integer bigCategoryId = 1;
		String mediumCategoryName = "mediumCategory";
		Category category = new Category();
		category.setId(2);
		when(repository.findMediumCategoryIdByName(bigCategoryId, mediumCategoryName)).thenReturn(category);

		// テスト実行
		Integer mediumCategoryId = service.findMediumCategoryIdByName(bigCategoryId, mediumCategoryName);

		// 結果の検証
		assertEquals(2, mediumCategoryId);
	}
}
