package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.EditItem;
import com.example.repository.ItemsRepository;

/**
 * item編集操作に関与するservice.
 * 
 * @author shibatamasayuki
 *
 */
@Service
@Transactional
public class EditItemService {

	@Autowired
	ItemsRepository itemsRepositpry;
	
	/**
	 * itemを編集する.
	 * 
	 * @param editItem editItem
	 */
	public void editItem(EditItem editItem) {
		itemsRepositpry.update(editItem);
	}
}
