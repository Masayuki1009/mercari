package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * user登録に関与するController.
 * 
 * @author shibatamasayuki
 *
 */
@Transactional
@Service
public class RegisterService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository repository;
	
	/**
	 * ユーザーを新しく登録する.
	 * 
	 * @param user user
	 */
	public void register(User user) {
		//passwordのhash化
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repository.insert(user);
	}
}
