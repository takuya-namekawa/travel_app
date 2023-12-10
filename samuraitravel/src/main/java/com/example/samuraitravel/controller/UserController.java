package com.example.samuraitravel.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.UserEditForm;
import com.example.samuraitravel.repository.UserRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	private final UserRepository userRepository;
	private final UserService userService;
	
	public UserController(UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}
	
	//�{���p
	@GetMapping
	//@Authenticationcipal -> ���݃��O�C�����̃��[�U�����󂯎�鎖���o����@�E�ӂɃA�m�e�[�V������t����I�u�W�F�N�g���w�肷��
	//����A�m�e�[�V������t���Ă���̂�UserDetailsImpl�ł��̃N���X�̓��[���A�h���X��p�X���[�h�A���[������ێ����Ă��邪���O�C����񂵂������Ă��Ȃ�
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model ) {
		//id������������Ȃ��Ɖ{������user������ł��Ȃ����߁A�ȉ��̍\���ň�������
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		
		model.addAttribute("user", user);
		
		return "user/index";
	}
	
	@GetMapping("/edit")
	public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		
		UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getFurigana(), user.getPostalCode(),user.getPhoneNumber(), user.getAddress(), user.getEmail());
		model.addAttribute("userEditForm", userEditForm);
		
		return "user/edit";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		//���[���A�h���X���ύX����Ă���A���o�^�ς݂ł���΁ABindingResult�I�u�W�F�N�g�ɃG���[���e��ǉ�����
		if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "���łɓo�^�ς݂̃��[���A�h���X�ł�");
			bindingResult.addError(fieldError);
		}
		
		if (bindingResult.hasErrors()) {
			return "user/edit";
		}
		userService.update(userEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "�������ҏW���܂���");
		
		return "redirect:/user";
	}
}
