package com.example.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.NewItem;
import com.example.repository.NewItemRepository;

@SpringBootTest
public class AddNewItemServiceTest {
	
	@Mock
	NewItemRepository repository;

	@InjectMocks
	AddNewItemService service;

	@DisplayName("addNewItemメソッドの動作確認")
	@Test
	public void isWorkingGood() {
		 // テスト対象の引数
        NewItem newItem = new NewItem();
        
        newItem.setId(1);
		newItem.setName("good shoes");
		newItem.setCondition(1);
		newItem.setCategory(3);
		newItem.setBrand("adidas");
		newItem.setPrice(100);
		newItem.setShipping(1);
		newItem.setDescription("good condition");

        // テスト実行
        service.addNewItem(newItem);

        // Mock上でrepository.insertが呼び出されたかどうかの確認を行うメソッド
        verify(repository).insert(newItem);

	}
}
