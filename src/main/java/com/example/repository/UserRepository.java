package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * table操作を行うRepository.
 * 
 * @author shibatamasayuki
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<User> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

	/**
	 * ユーザーを登録する.
	 * 
	 * @param user user
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "Insert into users(name, password, authority) values(:username, :password, :authority);";
		template.update(sql, param);
	}

	/**
	 * 受け取ったusernameとpasswordの情報と一致するuserを取得する.
	 * 
	 * @param user user
	 * @return user情報
	 */
	public User findByUserInfo(User user) {
		try {
			String sql = "SELECT id, name AS username, password, authority FROM users WHERE name = :name AND password = :password;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("name", user.getUsername())
					.addValue("password", user.getPassword());
			User loginUser = template.queryForObject(sql, param, USER_ROW_MAPPER);
			System.out.println("repository loginUser : " + loginUser);
			return loginUser;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
