package com.example.samuraitravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;
//JpaRepository<�G���e�B�e�B�̃N���X�^, ��L�[�̃f�[�^�^>
public interface HouseRepository extends JpaRepository<House, Integer>{

}
//JpaRepository�C���^�[�t�F�C�X���p�����邾���ŁA��{�I��CRUD������s�����\�b�h�����p�\�ɂȂ�
//findAll():�e�[�u�����̂��ׂẴG���e�B�e�B���擾����
//getReferenceById(id):�w�肵��id�̃G���e�B�e�B���擾����
//save(�G���e�B�e�B):�w�肵���G���e�B�e�B��ۑ�����܂��͍X�V����
//delete(�G���e�B�e�B):�w�肵���G���e�B�e�B���폜����
//deleteById(id):�w�肵��id�̃G���e�B�e�B���폜����
//JpaRepository�C���^�[�t�F�C�X�̌p�����ɂ͈ȉ��̏����ŋL�q


