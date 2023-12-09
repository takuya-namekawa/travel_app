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
	//メール送信用
	private final JavaMailSender javaMailSender;
	
	public SignupEventListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
		this.verificationTokenService = verificationTokenService;
		this.javaMailSender = mailSender;
	}
	//SignupEventオブジェクトを引数に設定しているため、SignupEventクラスから通知を受けた時onSignupEventメソッドが実行される
	@EventListener
	private void onSignupEvent(SignupEvent signupEvent) {
		User user = signupEvent.getUser();
		//トークンはUUIDで生成する
		String token = UUID.randomUUID().toString();
		verificationTokenService.create(user,  token);
		
		//メール内容作成の変数とその中身を定義していく
		String recipientAddress = user.getEmail();
		String subject = "メール認証";
		//生成したトークンをメール認証用のURLにパラメータとして埋め込みメールのメッセージ内に記載
		String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token;
		String message = "以下のリンクをクリックして会員登録を完了してください";
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		//送信先のメールアドレスをセット
		mailMessage.setTo(recipientAddress);
		//件名をセット
		mailMessage.setSubject(subject);
		//本文をセット
		mailMessage.setText(message + "\n" + confirmationUrl);
		javaMailSender.send(mailMessage);
	}
}
