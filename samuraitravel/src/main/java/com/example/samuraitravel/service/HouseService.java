package com.example.samuraitravel.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.form.HouseRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
//@Serviceを付ける事でこのクラスはサービスクラスとなる
@Service
public class HouseService {
	private final HouseRepository houseRepository;
	
	public HouseService(HouseRepository houseRepository) {
		this.houseRepository = houseRepository;
	}
	
	//@Transactinalを付ける事でそのメソッドをトランザクション化する事が出来る データの整合性を保ことが出来る
	//トランザクションとは、データベースの操作をひとまとまりにした処理
	//メソッドが正常に完了すれば、最後のsaveで確定されるし、途中で中断すればメソッド処理は破棄される　要するに変なデータが作成されたりする事を防ぐ
	@Transactional
	public void create(HouseRegisterForm houseRegisterForm) {
		//エンティティであるHouseクラスをインスタンス化する　エンティティのセッターを使用して値を受け取るため
		House house = new House();
		
		//ここからは送信された画像ファイルをstorageフォルダに保存していく処理
		//imageFileへフォームから受け取った画像ファイルを格納する
		MultipartFile imageFile = houseRegisterForm.getImageFile();
		
		//もし格納したファイルの情報が空でなければ以下を実行
		if (!imageFile.isEmpty()) {
			//フォームから受け取ったファイルネームをString型でimageNameとして格納 getOriginalFilename()はMultipartFileインターフェイスのメソッド
			String imageName = imageFile.getOriginalFilename();
			//呼び出し元に帰ってきたファイルネームを格納する
			String hashedImageName = generateNewName(imageName);
			//ファイルバスを作成する　格納場所指定みたいな
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			//ファイルのコピー処理
			copyImageFile(imageFile, filePath);
			//セッターで一意のImageNameをセットする
			house.setImageName(hashedImageName);	
		}
		//フォームから受け取った値をゲッターで受け取ってそれをセッターで格納する
		house.setName(houseRegisterForm.getName());
		house.setDescription(houseRegisterForm.getDescription());
		house.setPrice(houseRegisterForm.getPrice());
		house.setCapacity(houseRegisterForm.getCapacity());
		house.setPostalCode(houseRegisterForm.getPostalCode());
		house.setAddress(houseRegisterForm.getAddress());
		house.setPhoneNumber(houseRegisterForm.getPhoneNumber());
		//確定処理
		houseRepository.save(house);
	}
	
	//UUIDを使用して生成したファイルを返す 意図はファイル名の重複を防ぐための処理
	//UUIDとは、重複しない一意のIDの事
	public String generateNewName(String fileName) {
		//fileNameへ格納された情報はsplitメソッドで引数で指定した区切り文字で分割される　それを配列のfileNamesへ格納する
		String[] fileNames = fileName.split("\\.");
		//配列の要素数の-1回分繰り返す
		for (int i = 0; i < fileNames.length -1; i++) {
			//重複しないファイルネームをココで生成して要素数分格納する
			fileNames[i] = UUID.randomUUID().toString();
		}
		//.を文字列結合したファイルネームをhashedFileNameへ格納する
		String hashedFileName = String.join(".", fileNames);
		//呼び出し元に返す
		return hashedFileName;
	}
	
	//画像ファイルを指定したファイルにコピーする
	public void copyImageFile(MultipartFile imageFile, Path filePath) {
		try {
			Files.copy(imageFile.getInputStream(), filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
