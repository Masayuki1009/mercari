package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.domain.User;
import com.example.repository.UserRepository;

@SpringBootTest
public class RegisterServiceTest {

	@Mock
	UserRepository repository;

	@InjectMocks
	RegisterService service;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("パスワードハッシュ化 & 動作確認")
	public void testIsPasswordHashed() {
		User user = new User();
		user.setUsername("masa");
		user.setPassword("abc");
		String rawPW = user.getPassword();
		
		//期待値の設定(hash時)
		when(passwordEncoder.encode(rawPW)).thenReturn("hashed_password");
		String hashed_PW = passwordEncoder.encode(rawPW);

		//実行
		service.register(user);
		
		//hash化の動作確認
		assertEquals("hashed_password", hashed_PW);
		//メソッドの呼び出し確認
		verify(repository).insert(user);

	}

}
