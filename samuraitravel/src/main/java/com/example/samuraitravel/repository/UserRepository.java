package com.example.samuraitravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.User;
//���[���A�h���X�����p���\�b�h
public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByEmail(String email);
}
