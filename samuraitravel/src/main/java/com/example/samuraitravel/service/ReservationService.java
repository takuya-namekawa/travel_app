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
	//ログイン中ユーザの予約関連
	private final ReservationRepository reservationRepository;
	//民宿情報
	private final HouseRepository houseRepository;
	//ユーザ情報
	private final UserRepository userRepository;
	
	public ReservationService(ReservationRepository reservationRepository, HouseRepository houseRepository, UserRepository userRepository) {
		this.reservationRepository = reservationRepository;
		this.houseRepository = houseRepository;
		this.userRepository = userRepository;
	}
	
	//予約情報作成
	@Transactional
	//予約作成フォームのオブジェクトを引数で渡す
	public void create(ReservationRegisterForm reservationRegisterForm) {
		//予約のエンティティをオブジェクト作成
		Reservation reservation = new Reservation();
		//民宿情報を取得したい　民宿のID情報を取得したいが、予約入力のされた民宿のID情報が欲しい
		House house = houseRepository.getReferenceById(reservationRegisterForm.getHouseId());
		//予約したユーザのID情報を取得する
		User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
		//チェックイン日を取得する
		LocalDate checkinDate = LocalDate.parse(reservationRegisterForm.getChekinDate());
		//チェックアウト日を取得する
		LocalDate checkoutDate = LocalDate.parse(reservationRegisterForm.getChekoutDate());
		
		reservation.setHouse(house);
		reservation.setUser(user);
		reservation.setCheckinDate(checkinDate);
		reservation.setCheckOutDate(checkoutDate);
		reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());
		reservation.setAmount(reservationRegisterForm.getAmount());
		
		reservationRepository.save(reservation);
	}
	
	
	//宿泊人数が定員以下かどうかをチェックする
	public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
		//宿泊人数が定員数以下であれは、true
		return numberOfPeople <= capacity;
	}
	
	//宿泊料金を計算する
	public Integer calculateAmount(LocalDate checkinDate, LocalDate checkoutDate, Integer price) {
		//DAYS -> 1日の概念を表す単位
		//between -> 二つの時間的オブジェクトの間の時間量を計算する つまり宿泊日数を計算している
		//longなのはきまりみたい
		long numberOfNights = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
		//時間量をintにキャストして値段をかけて価格を出力
		int amount = price * (int)numberOfNights;
		return amount;
	}
}
