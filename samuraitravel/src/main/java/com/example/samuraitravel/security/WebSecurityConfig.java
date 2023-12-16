package com.example.samuraitravel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//���̃N���X���ݒ�p�Ƃ��ċ@�\����@���\�b�h��@Bean��t���邽�߂ɕK�v�ɂȂ�
@Configuration
//SpringSecurity�̋@�\��L���ɂ��ĔF�؁E���̃��[���⃍�O�C���E���O�A�E�g�̏����ȂǊe��ݒ���s����
@EnableWebSecurity
//���\�b�h���x���ŃZ�L�����e�B�@�\��L���ɂ���
@EnableMethodSecurity
public class WebSecurityConfig {
	//DI�R���e�i�ŊǗ������C���X�^���X
	@Bean
	//�N�ɂǂ̃y�[�W�ւ̃A�N�Z�X�������邩��ݒ肷�郁�\�b�h
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((requests) -> requests
			//�S�Ẵ��[�U�ɃA�N�Z�X��������URL URL��/**�͂��̃p�X�ȉ��̑S�Ẵt�@�C�����ΏۂɂȂ�
			.requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**", "/houses").permitAll()
			//�Ǘ��҂ɂ̂݃A�N�Z�X��������URL�@���L��@EnableMethodSecurity�ŗL���ɂ������\�b�h
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
	//�p�X���[�h���n�b�V���l��������
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
