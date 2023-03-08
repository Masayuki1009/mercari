package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.example.domain.Items;
import com.example.repository.ItemsRepository;

@SpringBootTest
public class ShowItemsServiceTest {

	@Mock
	ItemsRepository repository;

	@InjectMocks
	ShowItemsService service;

	@DisplayName("showItemList動作確認")
	@Test
	public void isShowItemsServiceWorks() {
		// setup
		Items item = new Items();
		item.setName("shoes");
		List<Items> itemList = new ArrayList<>();
		itemList.add(item);
		Integer offsetPoint = 30;

		// expectation
		when(repository.findAll(offsetPoint)).thenReturn(itemList);

		// execute
		service.showItemList(offsetPoint);

		// assertion
		verify(repository).findAll(offsetPoint);
		assertFalse(itemList.isEmpty());

	}
	
	@DisplayName("getItemsCount動作確認")
	@Test
	public void isgetItemsCountWorks() {
		// setup
		Integer count = 5;

		// expectation
		when(repository.findItemsCount()).thenReturn(5);

		// execute
		Integer result = service.getItemsCount();

		// assertion
		verify(repository).findItemsCount();
		assertEquals(5, result);

	}

}
