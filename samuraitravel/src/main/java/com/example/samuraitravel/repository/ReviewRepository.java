package com.example.samuraitravel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	//���r���[��������ʂ�6�\���������@�܂��\�����鏇�Ԃ̓��r���[�����e���ꂽ���ɂ����� ���h�ɑ΂��ē��e���ꂽ�����Ӗ�����̂�House��Ώ�
	public List<Review> findTop6ByHouseOrderByCreatedAtDesc(House house);
	//�Ώۂ̃��[�U�Ɩ��h�̈ꌏ�ڂ��擾������
	public Review findFirstByHouseAndUser(House house, User user);
	//���h�̐��𐔂�����
	public long countByHouse(House house);
	//���h�̓��e���ꂽ���ɖ��h�����擾���ăy�[�W�l�[�V�����ŕ\���������@���r���[�ꗗ�y�[�W�p�̃��\�b�h
	public Page<Review> findByHouseOrderByCreatedAtDesc(House house, Pageable pageable);
	public List<Review> findAll();
}
