package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.example.domain.User;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	private JdbcTemplate template;

	@Autowired
	UserRepository repository;
	
	@BeforeEach
	public void setup() {
	}

	@DisplayName("insert、findByUsernameテスト")
	@Test
	public void testInsertAndfindByUsername() {
		User user = new User();
		user.setUsername("masa");
		user.setPassword("Shibata20");
		user.setAuthority(1);

		repository.insert(user);

		User user1 = repository.findByUsername("masa");
		System.out.println("user : " + user1);

		assertEquals("masa", user.getUsername());
	}
	
	
}
