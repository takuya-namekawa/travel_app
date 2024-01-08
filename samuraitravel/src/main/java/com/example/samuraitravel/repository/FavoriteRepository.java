package com.example.samuraitravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{
	//対象のユーザと民宿を取得する 受け取るデータ型はリストではなくて大丈夫　1人のユーザは、複数民宿をお気に入り出来るが同じ民宿をお気に入りする事が出来ないため
	public Favorite findByHouseAndUser(House house, User user);
	//ページネーションを活用した一覧ペーシを作成する　お気に入りした民宿情報をお気に入りした降順で取得する
	public Page<Favorite> findByHouseOrderByCreatedAtDesc(House house, Pageable pageable);
}

