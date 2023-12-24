package com.example.samuraitravel.form;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationInputForm {
	@NotBlank(message = "�`�F�b�N�C�����ƃ`�F�b�N�A�E�g����I�����Ă�������")
	private String fromCheckinDateToCheckoutDate;
	
	@NotNull(message = "�h���l������͂��Ă�������")
	@Min(value = 1, message = "�h���l����1�l�ȏ�ɐݒ肵�Ă�������")
	private Integer numberOfPeople;
	
	//�`�F�b�N�C�������擾����
	public LocalDate getCheckinDate() {
		String[] checkinDateAndCheckoutDate = getFromCheckinDateToCheckoutDate().split("����");
		return LocalDate.parse(checkinDateAndCheckoutDate[0]);
	}
	
	//�`�F�b�N�A�E�g�����擾����
	public LocalDate getCheckoutDate() {
		String[] checkinDateAndCheckoutDate = getFromCheckinDateToCheckoutDate().split("����");
		return LocalDate.parse(checkinDateAndCheckoutDate[1]);
	}
}
