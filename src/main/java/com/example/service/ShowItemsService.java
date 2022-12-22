package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Items;
import com.example.repository.ItemsRepository;

/**
 * itemテーブルへのアクセス結果を返すservice.
 * 
 * @author shibatamasayuki
 *
 */
@Service
@Transactional
public class ShowItemsService {
	@Autowired
	ItemsRepository repository;

	/**
	 * repositoryのfindAllの結果を返す.
	 * 
	 * @return item 30件
	 */
	public List<Items> showItemList() {
		return repository.findAll();
	}

}
