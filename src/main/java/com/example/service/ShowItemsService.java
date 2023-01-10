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
	public List<Items> showItemList(Integer offsetPoint) {
		return repository.findAll(offsetPoint);
	}
	
	/**
	 * itemsテーブルの商品数の結果を返す.
	 * 
	 * @return itemsテーブルの商品数の合計
	 */
	public Integer getItemsCount() {
		return repository.findItemsCount();
	}

}
