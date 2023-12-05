package com.example.samuraitravel.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Role;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.SignupForm;
import com.example.samuraitravel.repository.RoleRepository;
import com.example.samuraitravel.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public User create(SignupForm signupForm) {
		User user = new User();
		//���[������ROLE_GENERAL��Role�I�u�W�F�N�g���擾
		Role role = roleRepository.findByName("ROLE_GENERAL");
		
		user.setName(signupForm.getName());
		user.setFurigana(signupForm.getFurigana());
		user.setPostalCode(signupForm.getPostalCode());
		user.setAddress(signupForm.getAddress());
		user.setPhoneNumber(signupForm.getPhoneNumber());
		user.setEmail(signupForm.getEmail());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
		user.setRole(role);
		//���[���F�؍ς݂��ǂ����𔻒f
		user.setEnabled(true);
		
		return userRepository.save(user);
		
	}
	
	//���[���A�h���X���o�^�ς݂��ǂ������`�F�b�N����
	public boolean isEmailRegistered(String email) {
		//�����Ŏ����email��User�I�u�W�F�N�g����T���āA�i�[����@���̍ۂȂɂ��Ȃ����null������̂�false �����true
		User user = userRepository.findByEmail(email);
		return user != null;
	}
	
	//�p�X���[�h�ƃp�X���[�h(�m�F�p)�̓��͒l����v���邩�ǂ���
	public boolean isSamePassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}
}
