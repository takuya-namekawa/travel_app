package com.example.samuraitravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//�F�؋@�\�@����o�^�p�̃R���g���[��
@Controller
public class AuthController {
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}
	
}
