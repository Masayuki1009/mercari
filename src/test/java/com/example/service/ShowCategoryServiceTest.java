package com.example.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Category;
import com.example.repository.CategoryRepository;

@SpringBootTest
public class ShowCategoryServiceTest {

	@Mock
	CategoryRepository repository;

	@InjectMocks
	ShowCategoryService service;

	@DisplayName("findAllBigCategoryメソッドテスト")
	@Test
	public void isFindAllBigCategoryMethodWorks() {
		// Mock category setup
		Category category1 = new Category();
		category1.setId(1);
		category1.setName("Men");
		category1.setNameAll(null);
		category1.setParent(null);

		// Mock List setup
		List<Category> List = new ArrayList<>();
		List.add(category1);

		// set expectation
		when(repository.findAll()).thenReturn(List);

		// execution
		List<Category> categoryList = service.findAllBigCategory();

		// assertion
		assertFalse(categoryList.isEmpty());
		verify(repository).findAll();

	}

	@DisplayName("FindMediumCategoryメソッドテスト")
	@Test
	public void isFindMediumCategoryMethodWorks() {
		// Mock category setup
		Category bigCategory = new Category();
		bigCategory.setId(1);
		bigCategory.setName("Men");
		bigCategory.setNameAll(null);
		bigCategory.setParent(null);

		Category mediumCategory = new Category();
		mediumCategory.setId(2);
		mediumCategory.setName("Clothe");
		mediumCategory.setNameAll(null);
		mediumCategory.setParent(1);

		// Mock List setup
		List<Category> bigList = new ArrayList<>();
		bigList.add(bigCategory);

		List<Category> mediumList = new ArrayList<>();
		mediumList.add(mediumCategory);

		// set expectation
		when(repository.findMediumCategoryById(bigCategory.getId())).thenReturn(mediumList);

		// execution
		List<Category> categoryList = service.findMediumCategory(bigCategory.getId());

		// assertion
		assertFalse(categoryList.isEmpty());
		verify(repository).findMediumCategoryById(bigCategory.getId());

	}

	@DisplayName("FindSmallCategoryメソッドテスト")
	@Test
	public void isFindSmallCategoryWorks() {
		// Mock category setup
		Category bigCategory = new Category();
		bigCategory.setId(1);
		bigCategory.setName("Men");
		bigCategory.setNameAll(null);
		bigCategory.setParent(null);

		Category mediumCategory = new Category();
		mediumCategory.setId(2);
		mediumCategory.setName("Clothes");
		mediumCategory.setNameAll(null);
		mediumCategory.setParent(1);

		Category smallCategory = new Category();
		smallCategory.setId(3);
		smallCategory.setName("Shoes");
		smallCategory.setNameAll("Men/Clothes/Shoes");
		smallCategory.setParent(2);

		// Mock List setup
		List<Category> bigList = new ArrayList<>();
		bigList.add(bigCategory);

		List<Category> mediumList = new ArrayList<>();
		mediumList.add(mediumCategory);

		List<Category> smallList = new ArrayList<>();
		smallList.add(smallCategory);

		// set expectation
		when(repository.findSmallCategoryById(mediumCategory.getId())).thenReturn(smallList);

		// execution
		List<Category> cateList = service.findSmallCategory(mediumCategory.getId());

		// assertion
		assertFalse(cateList.isEmpty());
		verify(repository).findSmallCategoryById(mediumCategory.getId());

	}

}
