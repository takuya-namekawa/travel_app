package com.example.samuraitravel.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {
	//�h���l��������ȉ����ǂ������`�F�b�N����
	public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
		//�h���l����������ȉ��ł���́Atrue
		return numberOfPeople <= capacity;
	}
	
	//�h���������v�Z����
	public Integer calculateAmount(LocalDate checkinDate, LocalDate checkoutDate, Integer price) {
		//DAYS -> 1���̊T�O��\���P��
		//between -> ��̎��ԓI�I�u�W�F�N�g�̊Ԃ̎��ԗʂ��v�Z���� �܂�h���������v�Z���Ă���
		//long�Ȃ̂͂��܂�݂���
		long numberOfNights = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
		//���ԗʂ�int�ɃL���X�g���Ēl�i�������ĉ��i���o��
		int amount = price * (int)numberOfNights;
		return amount;
	}
}
