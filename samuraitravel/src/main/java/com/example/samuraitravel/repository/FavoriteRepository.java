package com.example.samuraitravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{
	//�Ώۂ̃��[�U�Ɩ��h���擾���� �󂯎��f�[�^�^�̓��X�g�ł͂Ȃ��đ��v�@1�l�̃��[�U�́A�������h�����C�ɓ���o���邪�������h�����C�ɓ��肷�鎖���o���Ȃ�����
	public Favorite findByHouseAndUser(House house, User user);
	//�y�[�W�l�[�V���������p�����ꗗ�y�[�V���쐬����@���C�ɓ��肵�����h�������C�ɓ��肵���~���Ŏ擾����
	public Page<Favorite> findByHouseOrderByCreatedAtDesc(House house, Pageable pageable);
}

