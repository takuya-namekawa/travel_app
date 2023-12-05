package com.example.samuraitravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.form.SignupForm;
import com.example.samuraitravel.service.UserService;
//�F�؋@�\�@����o�^�p�̃R���g���[��
@Controller
public class AuthController {
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return "auth/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		//���[���A�h���X���o�^�ς݂ł����BindingResult�I�u�W�F�N�g�ɃG���[��ǉ�����
		if (userService.isEmailRegistered(signupForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "���łɓo�^�ς݂̃��[���A�h���X�ł�");
			bindingResult.addError(fieldError)
;		}
		
		// �p�X���[�h�ƃp�X���[�h�i�m�F�p�j�̓��͒l����v���Ȃ���΁ABindingResult�I�u�W�F�N�g�ɃG���[���e��ǉ�����
		if (!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "�p�X���[�h����v���܂���");
			bindingResult.addError(fieldError);
		}
		
		if (bindingResult.hasErrors()) {
			return "auth/signup";
		}
		
		userService.create(signupForm);
		redirectAttributes.addFlashAttribute("successMessege", "����o�^���������܂���");
		
		return "redirect:/";
		
	}
}
