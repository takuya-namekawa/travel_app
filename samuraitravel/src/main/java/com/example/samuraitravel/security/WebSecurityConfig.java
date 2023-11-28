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
			//�S�Ẵ��[�U�ɃA�N�Z�X��������URL
			.requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/").permitAll()
			//�Ǘ��҂ɂ̂݃A�N�Z�X��������URL
			.requestMatchers("/admin/**").hasRole("ADMIN")
			//��L�ȊO��URL�̓��O�C�����K�v
			.anyRequest().authenticated()
		)
		.formLogin((form) -> form
			//���O�C���y�[�W��URL	
			.loginPage("/login")
			//���O�C���t�H�[���̑��M��
			.loginProcessingUrl("/login")
			//���O�C���������̃��_�C���N�g��
			.defaultSuccessUrl("/?loggedIn")
			//���O�C�����s���̃��_�C���N�g��
			.failureUrl("/login?error")
			.permitAll()
		)
		.logout((logout) -> logout
			//���O�A�E�g���̃��_�C���N�g��	
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
