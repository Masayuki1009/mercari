package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailServiceImpl userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(requests) -> requests.requestMatchers("/", "/login", "/signin", "/register", "/register/registration")
						.permitAll().anyRequest().authenticated())
				.formLogin().loginPage("/login")// ログインする時に遷移するパス?
				.loginProcessingUrl("/signin")// フォームのSubmitURL、このURLへリクエストが送られると認証処理が実行される
				.usernameParameter("username") // リクエストパラメータのname属性を明示
				.passwordParameter("password").defaultSuccessUrl("/", true) // 第1引数:デフォルトでログイン成功時に遷移させるパス
				.failureUrl("/login?error=true") // ログイン失敗に遷移させるパス
				.permitAll();

		http.csrf().disable(); // 無効にする.ajax使用時にエラーが出るのを回避するため.

		http.logout()
				// ログアウトに関する設定
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // ログアウトさせる際に遷移させるパス
				.logoutSuccessUrl("/login") // ログアウト後に遷移させるパス(ここではログイン画面を設定)
				.deleteCookies("JSESSIONID") // ログアウト後、Cookieに保存されているセッションIDを削除
				.invalidateHttpSession(true); // true:ログアウト後、セッションを無効にする false:セッションを無効にしない
		return http.build();
	}

	// passwordハッシュ化のための準備
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}