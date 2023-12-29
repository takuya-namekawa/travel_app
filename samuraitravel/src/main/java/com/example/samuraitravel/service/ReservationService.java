package com.example.samuraitravel.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Reservation;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReservationRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReservationRepository;
import com.example.samuraitravel.repository.UserRepository;

@Service
public class ReservationService {
	//���O�C�������[�U�̗\��֘A
	private final ReservationRepository reservationRepository;
	//���h���
	private final HouseRepository houseRepository;
	//���[�U���
	private final UserRepository userRepository;
	
	public ReservationService(ReservationRepository reservationRepository, HouseRepository houseRepository, UserRepository userRepository) {
		this.reservationRepository = reservationRepository;
		this.houseRepository = houseRepository;
		this.userRepository = userRepository;
	}
	
	//�\����쐬
	@Transactional
	//�\��쐬�t�H�[���̃I�u�W�F�N�g�������œn��
	public void create(ReservationRegisterForm reservationRegisterForm) {
		//�\��̃G���e�B�e�B���I�u�W�F�N�g�쐬
		Reservation reservation = new Reservation();
		//���h�����擾�������@���h��ID�����擾���������A�\����͂̂��ꂽ���h��ID��񂪗~����
		House house = houseRepository.getReferenceById(reservationRegisterForm.getHouseId());
		//�\�񂵂����[�U��ID�����擾����
		User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
		//�`�F�b�N�C�������擾����
		LocalDate checkinDate = LocalDate.parse(reservationRegisterForm.getChekinDate());
		//�`�F�b�N�A�E�g�����擾����
		LocalDate checkoutDate = LocalDate.parse(reservationRegisterForm.getChekoutDate());
		
		reservation.setHouse(house);
		reservation.setUser(user);
		reservation.setCheckinDate(checkinDate);
		reservation.setCheckOutDate(checkoutDate);
		reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());
		reservation.setAmount(reservationRegisterForm.getAmount());
		
		reservationRepository.save(reservation);
	}
	
	
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
