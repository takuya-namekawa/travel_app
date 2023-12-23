package com.example.samuraitravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.Reservation;
import com.example.samuraitravel.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{
	//���O�C�����̃��[�U�[�̗\��f�[�^��V�����Ɏ擾����
	public Page<Reservation> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
