package com.example.samuraitravel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;
//JpaRepository<�G���e�B�e�B�̃N���X�^, ��L�[�̃f�[�^�^>
public interface HouseRepository extends JpaRepository<House, Integer>{
	//�����p�C���^�[�t�F�[�X�ǉ�
	public Page<House> findByNameLike(String keyword, Pageable pageable);
	
	//���h���܂��͖ړI�n�Ō�������i�V�����j
	public Page<House> findByNameLikeOrAddressLikeOrderByCreatedAtDesc(String nameKeyword, String addressKeyword, Pageable pageable);
	
	//���h���܂��͖ړI�n�Ō�������i�h���������������j
	public Page<House> findByNameLikeOrAddressLikeOrderByPriceAsc(String nameKeyword, String addressKeyword, Pageable pageable);
	
	//�G���A�Ō�������i�V�����j
	public Page<House> findByAddressLikeOrderByCreatedAtDesc(String area, Pageable pageable);
	
	//�G���A�Ō�������i�h���������������j
	public Page<House> findByAddressLikeOrderByPriceAsc(String area, Pageable pageable);
	
	//1��������̗\�Z�Ō�������i�V�����j
	public Page<House> findByPriceLessThanEqualOrderByCreatedAtDesc(Integer price, Pageable pageable);
	
	//1��������̗\�Z�Ō�������i�h���������������j
	public Page<House> findByPriceLessThanEqualOrderByPriceAsc(Integer price, Pageable pageable);
	
	//���ׂẴf�[�^���擾����i�V�����j
	public Page<House> findAllByOrderByCreatedAtDesc(Pageable pageable);
	
	//���ׂẴf�[�^���擾����i�h���������������j
	public Page<House> findAllByOrderByPriceAsc(Pageable pageable);
	
	
	//�V���̖��h���X�g�i10���j
	public List<House> findTop10ByOrderByCreatedAtDesc();
}
//JpaRepository�C���^�[�t�F�C�X���p�����邾���ŁA��{�I��CRUD������s�����\�b�h�����p�\�ɂȂ�
//findAll():�e�[�u�����̂��ׂẴG���e�B�e�B���擾����
//getReferenceById(id):�w�肵��id�̃G���e�B�e�B���擾����
//save(�G���e�B�e�B):�w�肵���G���e�B�e�B��ۑ�����܂��͍X�V����
//delete(�G���e�B�e�B):�w�肵���G���e�B�e�B���폜����
//deleteById(id):�w�肵��id�̃G���e�B�e�B���폜����
//JpaRepository�C���^�[�t�F�C�X�̌p�����ɂ͈ȉ��̏����ŋL�q


