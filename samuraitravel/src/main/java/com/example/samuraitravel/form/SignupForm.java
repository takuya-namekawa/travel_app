package com.example.samuraitravel.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFurigana() {
		return furigana;
	}

	public void setFurigana(String furigana) {
		this.furigana = furigana;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	
}
