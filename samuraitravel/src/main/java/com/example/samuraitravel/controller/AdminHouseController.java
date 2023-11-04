package com.example.samuraitravel.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    //@RequestParamを引数に取りフォームから送信されたパラメータ(リクエストパラメータ)をその引数にバインドさせる事が出来る
    @GetMapping
    public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC)Pageable pageable, @RequestParam(name = "keyword", required = false)String keyword) {
    	//HouseRepositoryインターフェイスのfindAll()メソッドで全ての民宿データを取得する
//        Page<House> housePage = houseRepository.findAll(pageable);
    	
    	
    	Page<House> housePage;
    	//keywordがnullでなく空でもなければtrue
    	if (keyword != null && !keyword.isEmpty()) {
    		//houseRepositoryのfindByNameLikeメソッドを使用し引数にあいまい検索用のクエリを渡す　第二引数にpageableを渡す
    		housePage = houseRepository.findByNameLike("%" + keyword + "%", pageable);
    	} else {
    		//keywordがnullの場合、または、空であれば全件検索メソッドを呼び出す
    		housePage = houseRepository.findAll(pageable);
    	}
    	
    		
        //モデルクラスを使用してビューにデータを渡す
        model.addAttribute("housePage", housePage); 
        //フォームから受け取った情報をモデルへ
        model.addAttribute("keyword", keyword);
        
        return "admin/houses/index";
    }  
}