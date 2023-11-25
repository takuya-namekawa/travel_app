package com.example.samuraitravel.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.form.HouseEditForm;
import com.example.samuraitravel.form.HouseRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.service.HouseService;

@Controller
//���[�g�p�X�̊�l��ݒ肷��
@RequestMapping("/admin/houses")
public class AdminHouseController {
    private final HouseRepository houseRepository;
    private final HouseService houseService;
    
    //�R���X�g���N�^�ňˑ����̒���(DI)���s���@���̎����R���X�g���N�^�C���W�F�N�V�����Ƃ���
    //�{���͈ȉ���t���邪�R���X�g���N�^��1�̏ꍇ�͏ȗ��\
//    @Autowired
    public AdminHouseController(HouseRepository houseRepository, HouseService houseService) {
        this.houseRepository = houseRepository; 
        this.houseService = houseService;
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
    
  //�ڍ׃y�[�W�p
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
    	House house = houseRepository.getReferenceById(id);
    	
    	model.addAttribute("house", house);
    	
    	return "admin/houses/show";
    }
    
    //�o�^�y�[�W�p
    @GetMapping("/register")
    public String register(Model model) {
    	//�쐬�����t�H�[���p�̃N���X���I�u�W�F�N�g���쐬����model�ɓn��
    	model.addAttribute("houseRegisterForm", new HouseRegisterForm());
    	return "admin/houses/register";
    }
    
    //�o�^�����p
    //�t�H�[����post���M�ɂ��Ă��邽��PostMapping�ɂ���
    @PostMapping("/create")
    //@ModelAttribute�Ńt�H�[���N���X�̃C���X�^���X���o�C���h�@@Validated�Ńt�H�[���N���X�̃C���X�^���X�ɑ΂��ăo���f�[�V�������s���@@BindingResult�̓o���f�[�V�����̌��ʂ�ێ�����C���^�[�t�F�C�X�@@RedirectAttributes�̓��_�C���N�g��Ƀf�[�^��n�����߂̋@�\��񋟂���C���^�[�t�F�C�X
    public String create(@ModelAttribute @Validated HouseRegisterForm houseRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    	//�G���[���m�����register�֑J�ڂ�����
    	if (bindingResult.hasErrors()) {
    		return "admin/houses/register";
    	}
    	
    	//�G���[��������΁Acreate���s
    	houseService.create(houseRegisterForm);
    	//���_�C���N�g��֓n���p�����[�^��ݒ�
    	//addFlashAttribute�̓��_�C���N�g��֓n�����玩���I�ɍ폜����邽�߈�����g�p����f�[�^�̎��Ɏg�p����
    	redirectAttributes.addFlashAttribute("successMessage", "���h��o�^���܂���");
    	//�r���[���Ăяo���̂ł͂Ȃ����_�C���N�g������
    	return "redirect:/admin/houses";
    }
    
    //�ҏW�y�[�W�p
    @GetMapping("/{id}/edit")
    //PathVariable��URL�̈ꕔ�������Ƀo�C���h����
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
    	//�G���e�B�e�B�̊Y��id�������Ă���
    	House house = houseRepository.getReferenceById(id);
    	//�摜�t�@�C�����i�[����
    	String imageName = house.getImageName();
    	//houseEditForm�̃I�u�W�F�N�g�𐶐����Y��id�̎����Ă������geter�Ŏ擾����EditForm�p�Ƀp�����[�^��n��
    	HouseEditForm houseEditForm = new HouseEditForm(house.getId(), house.getName(), null, house.getDescription(), house.getPrice(), house.getCapacity(), house.getPostalCode(), house.getAddress(), house.getPhoneNumber());
    	
    	//�n���ꂽ�摜�t�@�C�����ƕҏW�O����model�ɓn��
    	model.addAttribute("imageName", imageName);
    	model.addAttribute("houseEditForm", houseEditForm);
    	//�Ăяo�����ɕԂ�
    	return "admin/houses/edit";
    }
}