package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Items;
import com.example.repository.ItemsRepository;

@SpringBootTest
public class ShowItemDetailServiceTest {

	@Mock
	ItemsRepository repository;

	@InjectMocks
	ShowItemDetailService service;

	@DisplayName("動作確認テスト")
	@Test
	public void isShowItemDetailWorks() {
		// create mock item
		Items item = new Items();
		item.setId(1);
		item.setName("shoes");
		item.setCondition(1);
		item.setCategory("Men/Clothe/Shoes");
		item.setBrand("nike");
		item.setPrice(100);
		item.setShipping(1);
		item.setDescription("good one");

		// expectation
		when(repository.load(item.getId())).thenReturn(item);

		// execution
		Items searchedItem = service.showItemDetail(item.getId());

		// assertion
		verify(repository).load(item.getId());
		assertEquals(item, searchedItem);

	}

	@DisplayName("複数の商品を登録している場合に、それぞれ取得できるかどうかのテスト")
	@Test
	void testShowItemDetailWithMultipleItems() {
		// 複数の商品情報を作成
		Items item1 = new Items();
		item1.setId(1);
		item1.setName("test item 1");

		Items item2 = new Items();
		item2.setId(2);
		item2.setName("test item 2");

		// 商品IDが1の場合に、商品情報1を返すように設定
		when(repository.load(1)).thenReturn(item1);

		// 商品IDが2の場合に、商品情報2を返すように設定
		when(repository.load(2)).thenReturn(item2);

		// 登録順に取得できるかどうかを確認
		assertEquals(item1, service.showItemDetail(1));
		assertEquals(item2, service.showItemDetail(2));
	}
}
