package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Items;
import com.example.repository.ItemsRepository;

/**
 * 商品情報を1件取得するService.
 * 
 * @author shibatamasayuki
 *
 */
@Transactional
@Service
public class ShowItemDetailService {
	@Autowired
	ItemsRepository repository;
	
	/**
	 * 商品情報を1件取得するService.
	 * 
	 * @param id itemのid
	 * @return item
	 */
	public Items showItemDetail(Integer id) {
		return repository.load(id);
	}
	
}
