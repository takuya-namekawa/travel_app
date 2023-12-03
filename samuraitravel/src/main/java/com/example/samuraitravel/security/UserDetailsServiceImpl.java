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
			//userオブジェクトの中にuserRepositoryのemail情報を格納する
			User user = userRepository.findByEmail(email);
			//上記のuserオブジェクトを使用してそのuserに基づいたロール名を変数に格納する
			String userRoleName = user.getRole().getName();
			//ロール名をオブジェクト型で格納するための入れ物を用意
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			//入れ物に作成したロール名の情報を格納アドする
			authorities.add(new SimpleGrantedAuthority(userRoleName));
			//その情報を基に、userSetailsimplの引数へ渡してオブジェクトを作成する
			return new UserDetailsImpl(user, authorities);
		} catch (Exception e) {
			throw new UsernameNotFoundException("ユーザが見つかりませんでした");
		}
	}
}
