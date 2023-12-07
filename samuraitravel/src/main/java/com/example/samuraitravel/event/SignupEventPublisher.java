package com.example.samuraitravel.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.samuraitravel.entity.User;

@Component
public class SignupEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public SignupEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	public void publishSignupEvent(User user, String requestUrl) {
		//引数に発行したいEventクラスのインスタンスを渡す
		//イベントを発生させたいタイミングでこのメソッドを呼び出せばオーケー
		applicationEventPublisher.publishEvent(new SignupEvent(this, user, requestUrl));
	}
}
