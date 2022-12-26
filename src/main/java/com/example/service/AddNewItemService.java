package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.NewItem;
import com.example.repository.NewItemRepository;

/**
 * item操作に関与するservice.
 * 
 * @author shibatamasayuki
 *
 */
@Service
@Transactional
public class AddNewItemService {

	@Autowired
	NewItemRepository repository;

	/**
	 * item追加を行う.
	 * 
	 * @param newItem newItem
	 */
	public void addNewItem(NewItem newItem) {
		repository.insert(newItem);
	}
}
