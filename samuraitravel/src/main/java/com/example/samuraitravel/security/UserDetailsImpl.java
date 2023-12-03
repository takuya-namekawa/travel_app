package com.example.samuraitravel.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.samuraitravel.entity.User;

public class UserDetailsImpl implements UserDetails{
	private final User user;
	private final Collection<GrantedAuthority> authorities;
	
	public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}
	
	public User getUser() {
		return user;
	}
	
	//�n�b�V�����ς݂̃p�X���[�h��Ԃ�
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	//���O�C�����ɗ��p���郆�[�U��(���[���A�h���X)��Ԃ�
	@Override
	public String getUsername() {
		return user.getEmail();
	}
	
	//���[���̃R���N�V������Ԃ�
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	//�A�J�E���g�������؂�łȂ����true��Ԃ�
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//���[�U�����b�N����Ă��Ȃ����true��Ԃ�
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//���[�U�̃p�X���[�h�������؂�łȂ����true��Ԃ�
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	//���[�U���L���ł����true��Ԃ�
	@Override
	public boolean isEnabled() {
		return user.getEnabled();
	}
	
}
