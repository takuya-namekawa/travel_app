package com.example.samuraitravel.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity //���̃N���X���G���e�B�e�B�Ƃ��ċ@�\����
@Table(name = "houses") //�Ή��Â���e�[�u�������w�肷��
@Data //�Q�b�^�[��Z�b�^�[��������������
public class House {
	@Id //��L�[�w��
	// ���L�̓e�[�u������AUTO_INCREMENT���w�肵���J�����𗘗p���Ēl�𐶐�����悤�ɂȂ�@�����̔�
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")// �t�B�[���h�Ƀ}�b�s���O�����J���������w��o����B
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
