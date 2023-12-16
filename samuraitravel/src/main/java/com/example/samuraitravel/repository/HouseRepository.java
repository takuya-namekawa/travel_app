package com.example.samuraitravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;
//JpaRepository<�G���e�B�e�B�̃N���X�^, ��L�[�̃f�[�^�^>
public interface HouseRepository extends JpaRepository<House, Integer>{
	//�����p�C���^�[�t�F�[�X�ǉ�
	public Page<House> findByNameLike(String keyword, Pageable pageable);
	
	public Page<House> findByNameLikeOrAddressLike(String nameKeyword, String addressKeyword, Pageable pageable);    
    public Page<House> findByAddressLike(String area, Pageable pageable);
    public Page<House> findByPriceLessThanEqual(Integer price, Pageable pageable);  
}
//JpaRepository�C���^�[�t�F�C�X���p�����邾���ŁA��{�I��CRUD������s�����\�b�h�����p�\�ɂȂ�
//findAll():�e�[�u�����̂��ׂẴG���e�B�e�B���擾����
//getReferenceById(id):�w�肵��id�̃G���e�B�e�B���擾����
//save(�G���e�B�e�B):�w�肵���G���e�B�e�B��ۑ�����܂��͍X�V����
//delete(�G���e�B�e�B):�w�肵���G���e�B�e�B���폜����
//deleteById(id):�w�肵��id�̃G���e�B�e�B���폜����
//JpaRepository�C���^�[�t�F�C�X�̌p�����ɂ͈ȉ��̏����ŋL�q


