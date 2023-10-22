package com.example.samuraitravel.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.repository.HouseRepository;

@Controller
//���[�g�p�X�̊�l��ݒ肷��
@RequestMapping("/admin/houses")
public class AdminHouseController {
    private final HouseRepository houseRepository;         
    
    //�R���X�g���N�^�ňˑ����̒���(DI)���s���@���̎����R���X�g���N�^�C���W�F�N�V�����Ƃ���
    //�{���͈ȉ���t���邪�R���X�g���N�^��1�̏ꍇ�͏ȗ��\
//    @Autowired
    public AdminHouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;                
    }	
    //�����ɂ͉���������Ă��Ȃ���RequestMapping�̃A�m�e�[�V�����ŋL�q����Ă���ʂ�A���[�g�����܂��Ă��邽�ߏ����Ȃ��Ă��I�[�P�[
    @GetMapping
    public String index(Model model) {
    	//HouseRepository�C���^�[�t�F�C�X��findAll()���\�b�h�őS�Ă̖��h�f�[�^���擾����
        List<House> houses = houseRepository.findAll();       
        //���f���N���X���g�p���ăr���[�Ƀf�[�^��n��
        model.addAttribute("houses", houses);             
        
        return "admin/houses/index";
    }  
}