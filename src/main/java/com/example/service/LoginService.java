package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ログイン関係のservice.
 * 
 * @author shibatamasayuki
 *
 */
@Transactional
@Service
public class LoginService {
	@Autowired
	UserRepository repository;

	//Spring Security使用のため不要
	/**
	 * ログインユーザーの情報を取得.
	 * 
	 * @param user user
	 * @return user
	 */
//	public User login(User user) {
//		return repository.findByUserInfo(user);
//	}
}
