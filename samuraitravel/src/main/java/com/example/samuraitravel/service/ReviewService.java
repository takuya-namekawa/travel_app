package com.example.samuraitravel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.ReviewRepository;

@Service
public class ReviewService {
	private ReviewRepository reviewRepository;
	
	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional
	public void create(House house, User user, ReviewRegisterForm reviewRegisterForm) {
		Review review = new Review();
		review.setHouse(house);
		review.setUser(user);
		review.setScore(reviewRegisterForm.getScore());
		review.setContent(reviewRegisterForm.getContent());
		
		reviewRepository.save(review);
	}
	
	
	
	
	public boolean reviewUser(House house, User user) {
		//�w�肳�ꂽ���[�U�Ɋւ���ŏ��̃��r���[���擾���āA���r���[�̗L�����擾���Ă���
		//�܂背�r���[���Ă��邩���Ă��Ȃ����𔻕ʂ��Ă���
		//���r���[���Ă���Ώ�񂪂���󂾂���null�ł͂Ȃ�
		//���e���ĂȂ���Ώ�񂪂Ȃ�����null�ƂȂ�
		//reviewUser�ɂ͏�L�̔���ɂ����true,false������A���̒l���g�p���ăR���g���[���ŏo�͓��e�𕪊򂳂���
		boolean reviewUser = reviewRepository.findFirstByHouseAndUser(house, user) != null;
		return reviewUser;
	}
}
