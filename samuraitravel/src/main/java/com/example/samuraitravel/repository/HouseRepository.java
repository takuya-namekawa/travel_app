package com.example.samuraitravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;
//JpaRepository<エンティティのクラス型, 主キーのデータ型>
public interface HouseRepository extends JpaRepository<House, Integer>{

}
//JpaRepositoryインターフェイスを継承するだけで、基本的なCRUD操作を行うメソッドが利用可能になる
//findAll():テーブル内のすべてのエンティティを取得する
//getReferenceById(id):指定したidのエンティティを取得する
//save(エンティティ):指定したエンティティを保存するまたは更新する
//delete(エンティティ):指定したエンティティを削除する
//deleteById(id):指定したidのエンティティを削除する
//JpaRepositoryインターフェイスの継承時には以下の書式で記述


