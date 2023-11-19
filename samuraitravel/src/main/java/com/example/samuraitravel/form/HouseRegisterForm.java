package com.example.samuraitravel.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HouseRegisterForm {
	@NotBlank(message = "���h������͂��Ă�������")
	private String name;
	
	private MultipartFile imageFile;
	
	@NotBlank(message = "��������͂��Ă�������")
	private String description;
	
	@NotNull(message = "�h����������͂��Ă�������")
	@Min(value = 1, message = "�h��������1�~�ȏ�ɐݒ肵�Ă�������")
	private Integer price;
	
	@NotNull(message = "�������͂��Ă�������")
	@Min(value = 1, message = "�����1�l�ȏ�ɐݒ肵�Ă�������")
	private Integer capacity;
	
	@NotBlank(message = "�X�֔ԍ�����͂��Ă�������")
	private String postalCode;
	
	@NotBlank(message = "�Z������͂��Ă�������")
	private String address;
	
	@NotBlank(message = "�d�b�ԍ�����͂��Ă�������")
	private String phoneNumber;

}
