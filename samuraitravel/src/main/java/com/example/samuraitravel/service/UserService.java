package com.example.samuraitravel.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Role;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.SignupForm;
import com.example.samuraitravel.form.UserEditForm;
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
		user.setEnabled(false);
		
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
	//���[�U����L���ɂ���  �F�ؐ��������ۂɎ��s���郁�\�b�h
	@Transactional
	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	
	@Transactional
	public void update(UserEditForm userEditForm) {
		User user = userRepository.getReferenceById(userEditForm.getId());
		
		user.setName(userEditForm.getName());
		user.setFurigana(userEditForm.getFurigana());
		user.setPostalCode(userEditForm.getPostalCode());
		user.setAddress(userEditForm.getAddress());
		user.setPhoneNumber(userEditForm.getPhoneNumber());
		user.setEmail(userEditForm.getEmail());
		
		userRepository.save(user);
	}
	
	//user�����X�V�����Ƃ��Ƀ��[���A�h���X���X�V���ꂽ�����`�F�b�N����
	public boolean isEmailChanged(UserEditForm userEditForm) {
		User currentUser = userRepository.getReferenceById(userEditForm.getId());
		return !userEditForm.getEmail().equals(currentUser.getEmail());
	}
}
