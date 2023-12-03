package com.example.samuraitravel.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupForm {
	@NotBlank(message = "��������͂��Ă�������")
	private String name;
	
	@NotBlank(message = "�t���K�i����͂��Ă�������")
	private String furigana;
	
	@NotBlank(message = "�X�֔ԍ�����͂��Ă�������")
	private String postalCode;
	
	@NotBlank(message = "�Z������͂��Ă�������")
	private String address;
	
	@NotBlank(message = "�d�b�ԍ�����͂��Ă�������")
	private String phoneNumber;
	
	@NotBlank(message = "���[���A�h���X����͂��Ă�������")
	@Email(message = "���[���A�h���X�͐������`���œ��͂��Ă�������")
	private String email;
	
	@NotBlank(message = "�p�X���[�h����͂��Ă�������")
	@Length(min = 8, message = "�p�X���[�h��8�����ȏ�œ��͂��Ă�������")
	private String password;
	
	@NotBlank(message = "�p�X���[�h(�m�F�p)����͂��Ă�������")
	private String passwordConfirmation;
	
}
