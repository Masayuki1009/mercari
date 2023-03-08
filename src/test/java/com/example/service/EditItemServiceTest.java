package com.example.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.EditItem;
import com.example.repository.ItemsRepository;

@SpringBootTest
public class EditItemServiceTest {

	@Mock
	ItemsRepository repository;

	@InjectMocks
	EditItemService service;
	
	@DisplayName("editNewItemメソッドの動作確認")
	@Test
	public void isWorkingGood() {
		 // テスト対象の引数
        EditItem editItem = new EditItem();
        
        editItem.setId(1);
        editItem.setName("good shoes");
        editItem.setCondition(1);
        editItem.setCategory(3);
        editItem.setBrand("adidas");
        editItem.setPrice(100);
        editItem.setShipping(1);
        editItem.setDescription("good condition");

        // テスト実行
        service.editItem(editItem);

        // Mock上でrepository.insertが呼び出されたかどうかの確認を行うメソッド
        verify(repository).update(editItem);

	}
	
	
}
