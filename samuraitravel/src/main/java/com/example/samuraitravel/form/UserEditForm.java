package com.example.samuraitravel.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEditForm {
	@NotNull
	private Integer id;
	
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
	private String email;
}
