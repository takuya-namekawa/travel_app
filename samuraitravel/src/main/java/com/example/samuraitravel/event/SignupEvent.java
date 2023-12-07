package com.example.samuraitravel.event;

import org.springframework.context.ApplicationEvent;

import com.example.samuraitravel.entity.User;

import lombok.Getter;
//イベント作成の基本的なクラス　ー＞　ApplicationEvent イベントソース(発生源)を保持する
//当クラスの役割 -> イベントが発生した事をListenerクラスに知らせる事とイベントに関する情報を保存する事
//@Getter -> Getterのみを自動生成する
@Getter
public class SignupEvent extends ApplicationEvent{
	private User user;
	private String requestUrl;
	//会員登録したユーザの情報とリクエストを受けたURLを保持する
	public SignupEvent(Object source, User user, String requestUrl) {
		//イベントの発生源を渡す
		super(source);
		
		this.user = user;
		this.requestUrl = requestUrl;
	}
}
