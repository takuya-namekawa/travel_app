package com.example.samuraitravel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//このクラスが設定用として機能する　メソッドに@Beanを付けるために必要になる
@Configuration
//SpringSecurityの機能を有効にして認証・許可のルールやログイン・ログアウトの処理など各種設定を行える
@EnableWebSecurity
//メソッドレベルでセキュリティ機能を有効にする
@EnableMethodSecurity
public class WebSecurityConfig {
	//DIコンテナで管理されるインスタンス
	@Bean
	//誰にどのページへのアクセスを許可するかを設定するメソッド
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((requests) -> requests
			//全てのユーザにアクセスを許可するURL URLの/**はそのパス以下の全てのファイルが対象になる
			.requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**", "/houses").permitAll()
			//管理者にのみアクセスを許可するURL　下記は@EnableMethodSecurityで有効にしたメソッド
			.requestMatchers("/admin/**").hasRole("ADMIN")
			//上記以外のURLはログインが必要
			.anyRequest().authenticated()
		)
		.formLogin((form) -> form
			//ログインページのURL	
			.loginPage("/login")
			//ログインフォームの送信先
			.loginProcessingUrl("/login")
			//ログイン成功時のリダイレクト先
			.defaultSuccessUrl("/?loggedIn")
			//ログイン失敗時のリダイレクト先
			.failureUrl("/login?error")
			.permitAll()
		)
		.logout((logout) -> logout
			//ログアウト時のリダイレクト先	
			.logoutSuccessUrl("/?loggedOut")
			.permitAll()
		);
	return http.build();
	}
	@Bean
	//パスワードをハッシュ値生成する
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
