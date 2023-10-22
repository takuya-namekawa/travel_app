package com.example.samuraitravel.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.repository.HouseRepository;

@Controller
//ルートパスの基準値を設定する
@RequestMapping("/admin/houses")
public class AdminHouseController {
    private final HouseRepository houseRepository;         
    
    //コンストラクタで依存性の注入(DI)を行う　この事をコンストラクタインジェクションという
    //本来は以下を付けるがコンストラクタが1つの場合は省略可能
//    @Autowired
    public AdminHouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;                
    }	
    //ここには何もかかれていないがRequestMappingのアノテーションで記述されている通り、ルートが決まっているため書かなくてもオーケー
    @GetMapping
    public String index(Model model) {
    	//HouseRepositoryインターフェイスのfindAll()メソッドで全ての民宿データを取得する
        List<House> houses = houseRepository.findAll();       
        //モデルクラスを使用してビューにデータを渡す
        model.addAttribute("houses", houses);             
        
        return "admin/houses/index";
    }  
}