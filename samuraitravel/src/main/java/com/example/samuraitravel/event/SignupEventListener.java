package com.example.samuraitravel.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.service.VerificationTokenService;

@Component
public class SignupEventListener {
	private final VerificationTokenService verificationTokenService;
	//���[�����M�p
	private final JavaMailSender javaMailSender;
	
	public SignupEventListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
		this.verificationTokenService = verificationTokenService;
		this.javaMailSender = mailSender;
	}
	//SignupEvent�I�u�W�F�N�g�������ɐݒ肵�Ă��邽�߁ASignupEvent�N���X����ʒm���󂯂���onSignupEvent���\�b�h�����s�����
	@EventListener
	private void onSignupEvent(SignupEvent signupEvent) {
		User user = signupEvent.getUser();
		//�g�[�N����UUID�Ő�������
		String token = UUID.randomUUID().toString();
		verificationTokenService.create(user,  token);
		
		//���[�����e�쐬�̕ϐ��Ƃ��̒��g���`���Ă���
		String recipientAddress = user.getEmail();
		String subject = "���[���F��";
		//���������g�[�N�������[���F�ؗp��URL�Ƀp�����[�^�Ƃ��Ė��ߍ��݃��[���̃��b�Z�[�W���ɋL��
		String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token;
		String message = "�ȉ��̃����N���N���b�N���ĉ���o�^���������Ă�������";
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		//���M��̃��[���A�h���X���Z�b�g
		mailMessage.setTo(recipientAddress);
		//�������Z�b�g
		mailMessage.setSubject(subject);
		//�{�����Z�b�g
		mailMessage.setText(message + "\n" + confirmationUrl);
		javaMailSender.send(mailMessage);
	}
}
