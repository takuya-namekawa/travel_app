package com.example.samuraitravel.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.form.HouseRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
//@Service��t���鎖�ł��̃N���X�̓T�[�r�X�N���X�ƂȂ�
@Service
public class HouseService {
	private final HouseRepository houseRepository;
	
	public HouseService(HouseRepository houseRepository) {
		this.houseRepository = houseRepository;
	}
	
	//@Transactinal��t���鎖�ł��̃��\�b�h���g�����U�N�V���������鎖���o���� �f�[�^�̐�������ۂ��Ƃ��o����
	//�g�����U�N�V�����Ƃ́A�f�[�^�x�[�X�̑�����ЂƂ܂Ƃ܂�ɂ�������
	//���\�b�h������Ɋ�������΁A�Ō��save�Ŋm�肳��邵�A�r���Œ��f����΃��\�b�h�����͔j�������@�v����ɕςȃf�[�^���쐬���ꂽ�肷�鎖��h��
	@Transactional
	public void create(HouseRegisterForm houseRegisterForm) {
		//�G���e�B�e�B�ł���House�N���X���C���X�^���X������@�G���e�B�e�B�̃Z�b�^�[���g�p���Ēl���󂯎�邽��
		House house = new House();
		
		//��������͑��M���ꂽ�摜�t�@�C����storage�t�H���_�ɕۑ����Ă�������
		//imageFile�փt�H�[������󂯎�����摜�t�@�C�����i�[����
		MultipartFile imageFile = houseRegisterForm.getImageFile();
		
		//�����i�[�����t�@�C���̏�񂪋�łȂ���Έȉ������s
		if (!imageFile.isEmpty()) {
			//�t�H�[������󂯎�����t�@�C���l�[����String�^��imageName�Ƃ��Ċi�[ getOriginalFilename()��MultipartFile�C���^�[�t�F�C�X�̃��\�b�h
			String imageName = imageFile.getOriginalFilename();
			//�Ăяo�����ɋA���Ă����t�@�C���l�[�����i�[����
			String hashedImageName = generateNewName(imageName);
			//�t�@�C���o�X���쐬����@�i�[�ꏊ�w��݂�����
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			//�t�@�C���̃R�s�[����
			copyImageFile(imageFile, filePath);
			//�Z�b�^�[�ň�ӂ�ImageName���Z�b�g����
			house.setImageName(hashedImageName);	
		}
		//�t�H�[������󂯎�����l���Q�b�^�[�Ŏ󂯎���Ă�����Z�b�^�[�Ŋi�[����
		house.setName(houseRegisterForm.getName());
		house.setDescription(houseRegisterForm.getDescription());
		house.setPrice(houseRegisterForm.getPrice());
		house.setCapacity(houseRegisterForm.getCapacity());
		house.setPostalCode(houseRegisterForm.getPostalCode());
		house.setAddress(houseRegisterForm.getAddress());
		house.setPhoneNumber(houseRegisterForm.getPhoneNumber());
		//�m�菈��
		houseRepository.save(house);
	}
	
	//UUID���g�p���Đ��������t�@�C����Ԃ� �Ӑ}�̓t�@�C�����̏d����h�����߂̏���
	//UUID�Ƃ́A�d�����Ȃ���ӂ�ID�̎�
	public String generateNewName(String fileName) {
		//fileName�֊i�[���ꂽ����split���\�b�h�ň����Ŏw�肵����؂蕶���ŕ��������@�����z���fileNames�֊i�[����
		String[] fileNames = fileName.split("\\.");
		//�z��̗v�f����-1�񕪌J��Ԃ�
		for (int i = 0; i < fileNames.length -1; i++) {
			//�d�����Ȃ��t�@�C���l�[�����R�R�Ő������ėv�f�����i�[����
			fileNames[i] = UUID.randomUUID().toString();
		}
		//.�𕶎��񌋍������t�@�C���l�[����hashedFileName�֊i�[����
		String hashedFileName = String.join(".", fileNames);
		//�Ăяo�����ɕԂ�
		return hashedFileName;
	}
	
	//�摜�t�@�C�����w�肵���t�@�C���ɃR�s�[����
	public void copyImageFile(MultipartFile imageFile, Path filePath) {
		try {
			Files.copy(imageFile.getInputStream(), filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
