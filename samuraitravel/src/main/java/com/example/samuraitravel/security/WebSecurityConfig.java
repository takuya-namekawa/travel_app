package com.example.samuraitravel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((requests) -> requests
			//全てのユーザにアクセスを許可するURL
			.requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/").permitAll()
			//管理者にのみアクセスを許可するURL
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
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
