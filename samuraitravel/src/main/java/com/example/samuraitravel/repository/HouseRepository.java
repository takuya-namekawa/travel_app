package com.example.samuraitravel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;
//JpaRepository<エンティティのクラス型, 主キーのデータ型>
public interface HouseRepository extends JpaRepository<House, Integer>{
	//検索用インターフェース追加
	public Page<House> findByNameLike(String keyword, Pageable pageable);
	
	//民宿名または目的地で検索する（新着順）
	public Page<House> findByNameLikeOrAddressLikeOrderByCreatedAtDesc(String nameKeyword, String addressKeyword, Pageable pageable);
	
	//民宿名または目的地で検索する（宿泊料金が安い順）
	public Page<House> findByNameLikeOrAddressLikeOrderByPriceAsc(String nameKeyword, String addressKeyword, Pageable pageable);
	
	//エリアで検索する（新着順）
	public Page<House> findByAddressLikeOrderByCreatedAtDesc(String area, Pageable pageable);
	
	//エリアで検索する（宿泊料金が安い順）
	public Page<House> findByAddressLikeOrderByPriceAsc(String area, Pageable pageable);
	
	//1泊あたりの予算で検索する（新着順）
	public Page<House> findByPriceLessThanEqualOrderByCreatedAtDesc(Integer price, Pageable pageable);
	
	//1泊あたりの予算で検索する（宿泊料金が安い順）
	public Page<House> findByPriceLessThanEqualOrderByPriceAsc(Integer price, Pageable pageable);
	
	//すべてのデータを取得する（新着順）
	public Page<House> findAllByOrderByCreatedAtDesc(Pageable pageable);
	
	//すべてのデータを取得する（宿泊料金が安い順）
	public Page<House> findAllByOrderByPriceAsc(Pageable pageable);
	
	
	//新着の民宿リスト（10件）
	public List<House> findTop10ByOrderByCreatedAtDesc();
}
//JpaRepositoryインターフェイスを継承するだけで、基本的なCRUD操作を行うメソッドが利用可能になる
//findAll():テーブル内のすべてのエンティティを取得する
//getReferenceById(id):指定したidのエンティティを取得する
//save(エンティティ):指定したエンティティを保存するまたは更新する
//delete(エンティティ):指定したエンティティを削除する
//deleteById(id):指定したidのエンティティを削除する
//JpaRepositoryインターフェイスの継承時には以下の書式で記述


