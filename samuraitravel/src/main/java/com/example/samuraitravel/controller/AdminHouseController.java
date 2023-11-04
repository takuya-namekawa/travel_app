package com.example.samuraitravel.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    //@RequestParam�������Ɏ��t�H�[�����瑗�M���ꂽ�p�����[�^(���N�G�X�g�p�����[�^)�����̈����Ƀo�C���h�����鎖���o����
    @GetMapping
    public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC)Pageable pageable, @RequestParam(name = "keyword", required = false)String keyword) {
    	//HouseRepository�C���^�[�t�F�C�X��findAll()���\�b�h�őS�Ă̖��h�f�[�^���擾����
//        Page<House> housePage = houseRepository.findAll(pageable);
    	
    	
    	Page<House> housePage;
    	//keyword��null�łȂ���ł��Ȃ����true
    	if (keyword != null && !keyword.isEmpty()) {
    		//houseRepository��findByNameLike���\�b�h���g�p�������ɂ����܂������p�̃N�G����n���@��������pageable��n��
    		housePage = houseRepository.findByNameLike("%" + keyword + "%", pageable);
    	} else {
    		//keyword��null�̏ꍇ�A�܂��́A��ł���ΑS���������\�b�h���Ăяo��
    		housePage = houseRepository.findAll(pageable);
    	}
    	
    		
        //���f���N���X���g�p���ăr���[�Ƀf�[�^��n��
        model.addAttribute("housePage", housePage); 
        //�t�H�[������󂯎�����������f����
        model.addAttribute("keyword", keyword);
        
        return "admin/houses/index";
    }  
}