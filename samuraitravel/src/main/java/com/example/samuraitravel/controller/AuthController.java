package com.example.samuraitravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.entity.VerificationToken;
import com.example.samuraitravel.event.SignupEventPublisher;
import com.example.samuraitravel.form.SignupForm;
import com.example.samuraitravel.service.UserService;
import com.example.samuraitravel.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;
//�F�؋@�\�@����o�^�p�̃R���g���[��
@Controller
public class AuthController {
	private final UserService userService;
	private final SignupEventPublisher signupEventPublisher;
	private final VerificationTokenService verificationTokenService;
	
	public AuthController(UserService userService, SignupEventPublisher signupEventPublisher, VerificationTokenService verificationTokenService) {
		this.userService = userService;
		this.signupEventPublisher = signupEventPublisher;
		this.verificationTokenService = verificationTokenService;
		
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
	public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
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
		
		User createdUser = userService.create(signupForm);
		String requestUrl = new String(httpServletRequest.getRequestURL());
		signupEventPublisher.publishSignupEvent(createdUser, requestUrl);
		redirectAttributes.addFlashAttribute("successMessage", "�����͂������������[���A�h���X�ɔF�؃��[���𑗐M���܂����B���[���ɋL�ڂ���Ă��郊���N���N���b�N���A����o�^���������Ă�������");
		
		return "redirect:/";
		
	}
	
	//���[���F�ؗp
	@GetMapping("/signup/verify")
	public String verify(@RequestParam(name = "token") String token, Model model) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		//�g�[�N�������݂���Ή����L���ɂ���
		if (verificationToken != null) {
			User user = verificationToken.getUser();
			//�g�[�N�������݂���̂ŁA���[���F�؂̃p�����[�^��false -> true�֐؂�ւ���@���̂��߂ɒ�`���Ă�����enableUser���\�b�h���Ăяo���ăZ�b�g����
			userService.enableUser(user);
			String successMessage = "����o�^���������܂���";
			model.addAttribute("successMessage", successMessage);
		} else {
			String errorMessage = "�g�[�N���������ł�";
			model.addAttribute("errorMessage", errorMessage);
		}
		
		return "auth/verify";
	}
}
