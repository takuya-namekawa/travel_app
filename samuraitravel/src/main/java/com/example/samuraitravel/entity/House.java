package com.example.samuraitravel.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity //このクラスがエンティティとして機能する
@Table(name = "houses") //対応づけるテーブル名を指定する
@Data //ゲッターやセッターを自動生成する
public class House {
	@Id //主キー指定
	// 下記はテーブル内のAUTO_INCREMENTを指定したカラムを利用して値を生成するようになる　自動採番
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")// フィールドにマッピングされるカラム名を指定出来る。
	private String  name;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "capacity")
	private Integer capacity;
	
	@Column(name = "postal_code")
	private String postalCode;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_number")
    private String phoneNumber;
	
	@Column(name = "created_at", insertable = false, updatable = false )
	private Timestamp createdAt;
	
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
	
	
}
