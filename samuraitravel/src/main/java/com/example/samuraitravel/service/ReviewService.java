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
		//指定されたユーザに関する最初のレビューを取得して、レビューの有無を取得している
		//つまりレビューしているかしていないかを判別している
		//レビューしていれば情報がある訳だからnullではない
		//投稿してなければ情報がないからnullとなる
		//reviewUserには上記の判定によってtrue,falseが入り、その値を使用してコントローラで出力内容を分岐させる
		boolean reviewUser = reviewRepository.findFirstByHouseAndUser(house, user) != null;
		return reviewUser;
	}
}
