package com.example.samuraitravel.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.repository.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			//user�I�u�W�F�N�g�̒���userRepository��email�����i�[����
			User user = userRepository.findByEmail(email);
			//��L��user�I�u�W�F�N�g���g�p���Ă���user�Ɋ�Â������[������ϐ��Ɋi�[����
			String userRoleName = user.getRole().getName();
			//���[�������I�u�W�F�N�g�^�Ŋi�[���邽�߂̓��ꕨ��p��
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			//���ꕨ�ɍ쐬�������[�����̏����i�[�A�h����
			authorities.add(new SimpleGrantedAuthority(userRoleName));
			//���̏�����ɁAuserSetailsimpl�̈����֓n���ăI�u�W�F�N�g���쐬����
			return new UserDetailsImpl(user, authorities);
		} catch (Exception e) {
			throw new UsernameNotFoundException("���[�U��������܂���ł���");
		}
	}
}
