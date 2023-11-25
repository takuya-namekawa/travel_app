package com.example.samuraitravel.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.form.HouseEditForm;
import com.example.samuraitravel.form.HouseRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.service.HouseService;

@Controller
//ルートパスの基準値を設定する
@RequestMapping("/admin/houses")
public class AdminHouseController {
    private final HouseRepository houseRepository;
    private final HouseService houseService;
    
    //コンストラクタで依存性の注入(DI)を行う　この事をコンストラクタインジェクションという
    //本来は以下を付けるがコンストラクタが1つの場合は省略可能
//    @Autowired
    public AdminHouseController(HouseRepository houseRepository, HouseService houseService) {
        this.houseRepository = houseRepository; 
        this.houseService = houseService;
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
    
  //詳細ページ用
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
    	House house = houseRepository.getReferenceById(id);
    	
    	model.addAttribute("house", house);
    	
    	return "admin/houses/show";
    }
    
    //登録ページ用
    @GetMapping("/register")
    public String register(Model model) {
    	//作成したフォーム用のクラスをオブジェクトを作成してmodelに渡す
    	model.addAttribute("houseRegisterForm", new HouseRegisterForm());
    	return "admin/houses/register";
    }
    
    //登録処理用
    //フォームはpost送信にしているためPostMappingにする
    @PostMapping("/create")
    //@ModelAttributeでフォームクラスのインスタンスをバインド　@Validatedでフォームクラスのインスタンスに対してバリデーションを行う　@BindingResultはバリデーションの結果を保持するインターフェイス　@RedirectAttributesはリダイレクト先にデータを渡すための機能を提供するインターフェイス
    public String create(@ModelAttribute @Validated HouseRegisterForm houseRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    	//エラー検知すればregisterへ遷移させる
    	if (bindingResult.hasErrors()) {
    		return "admin/houses/register";
    	}
    	
    	//エラーが無ければ、create実行
    	houseService.create(houseRegisterForm);
    	//リダイレクト先へ渡すパラメータを設定
    	//addFlashAttributeはレダイレクト先へ渡したら自動的に削除されるため一回限り使用するデータの時に使用する
    	redirectAttributes.addFlashAttribute("successMessage", "民宿を登録しました");
    	//ビューを呼び出すのではなくリダイレクトさせる
    	return "redirect:/admin/houses";
    }
    
    //編集ページ用
    @GetMapping("/{id}/edit")
    //PathVariableでURLの一部を引数にバインドする
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
    	//エンティティの該当idを持ってくる
    	House house = houseRepository.getReferenceById(id);
    	//画像ファイルを格納する
    	String imageName = house.getImageName();
    	//houseEditFormのオブジェクトを生成し該当idの持っている情報をgeterで取得してEditForm用にパラメータを渡す
    	HouseEditForm houseEditForm = new HouseEditForm(house.getId(), house.getName(), null, house.getDescription(), house.getPrice(), house.getCapacity(), house.getPostalCode(), house.getAddress(), house.getPhoneNumber());
    	
    	//渡された画像ファイル名と編集前情報をmodelに渡す
    	model.addAttribute("imageName", imageName);
    	model.addAttribute("houseEditForm", houseEditForm);
    	//呼び出し元に返す
    	return "admin/houses/edit";
    }
}