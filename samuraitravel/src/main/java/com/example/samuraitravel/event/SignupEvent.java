package com.example.samuraitravel.event;

import org.springframework.context.ApplicationEvent;

import com.example.samuraitravel.entity.User;

import lombok.Getter;
//�C�x���g�쐬�̊�{�I�ȃN���X�@�[���@ApplicationEvent �C�x���g�\�[�X(������)��ێ�����
//���N���X�̖��� -> �C�x���g��������������Listener�N���X�ɒm�点�鎖�ƃC�x���g�Ɋւ������ۑ����鎖
//@Getter -> Getter�݂̂�������������
@Getter
public class SignupEvent extends ApplicationEvent{
	private User user;
	private String requestUrl;
	//����o�^�������[�U�̏��ƃ��N�G�X�g���󂯂�URL��ێ�����
	public SignupEvent(Object source, User user, String requestUrl) {
		//�C�x���g�̔�������n��
		super(source);
		
		this.user = user;
		this.requestUrl = requestUrl;
	}
}
