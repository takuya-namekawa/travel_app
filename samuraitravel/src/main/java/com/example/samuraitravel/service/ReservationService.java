package com.example.samuraitravel.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {
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
