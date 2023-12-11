package com.example.samuraitravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.User;
//メールアドレス検索用メソッド
public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByEmail(String email);
	//名前とフリガナ検索用
	public Page<User> findByNameLikeOrFuriganaLike(String nameKeyword, String furiganaKeyword, Pageable pageble);
}
