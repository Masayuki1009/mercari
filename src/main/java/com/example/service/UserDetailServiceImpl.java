package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * Userのログイン認証を行うService. Spring
 * Securityを用いたログイン認証を行う場合、ログインボタンを押した際に、下記loadUserByUserNameメソッドにusernameが渡され処理が行われる.
 * 
 * @author shibatamasayuki
 *
 */
@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//userが存在するかどうかの判定
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		if (user.getAuthority() == 0) {
			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));// 管理者権限付与
			System.out.println("ROLE_ADMINの権限が付与されました。");
		} else if (user.getAuthority() == 1) {
			authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザー権限付与
			System.out.println("ROLE_USERの権限が付与されました。");
		} else {
			throw new BadCredentialsException("Authentication Error");
		}

		// loginUserクラスがorg.springframework.security.core.userdetails.Userを継承している=UserDetails型になっているのと同義(?)
		return new LoginUser(user, authorityList);
	}

}
