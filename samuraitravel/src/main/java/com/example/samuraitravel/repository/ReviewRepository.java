package com.example.samuraitravel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	//レビューを初期画面で6つ表示したい　また表示する順番はレビューが投稿された順にしたい 民宿に対して投稿された事を意味するのでHouseを対象
	public List<Review> findTop6ByHouseOrderByCreatedAtDesc(House house);
	//対象のユーザと民宿の一件目を取得したい
	public Review findFirstByHouseAndUser(House house, User user);
	//民宿の数を数えたい
	public long countByHouse(House house);
	//民宿の投稿された順に民宿情報を取得してページネーションで表示したい　レビュー一覧ページ用のメソッド
	public Page<Review> findByHouseOrderByCreatedAtDesc(House house, Pageable pageable);
	public List<Review> findAll();
}
