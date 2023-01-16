package com.example.form;

import jakarta.validation.constraints.NotBlank;

/**
 * user情報を格納するform.
 * 
 * @author shibatamasayuki
 *
 */
public class UserForm {
	@NotBlank(message="ユーザーネームを入力してください")
	private String username;
	@NotBlank(message="パスワードを入力してください")
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
}
