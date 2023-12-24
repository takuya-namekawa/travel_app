package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Reservation;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReservationInputForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReservationRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.ReservationService;

@Controller
public class ReservationController {
	private final ReservationRepository reservationRepository;
	private final HouseRepository houseRepository;
	private final ReservationService reservationService;
	
	
	public ReservationController(ReservationRepository reservationRepository, HouseRepository houseRepository, ReservationService reservationService) {
		this.reservationRepository = reservationRepository;
		this.houseRepository = houseRepository;
		this.reservationService = reservationService;
	}
	
	@GetMapping("/reservations")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model ) {
		User user = userDetailsImpl.getUser();
		Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
		
		model.addAttribute("reservationPage", reservationPage);
		
		return "reservations/index";
	}
	
	//予約フォームの送信先(/houses/{id}/reservations/input)を担当するinput()メソッドを定義する
	//役割…予約フォームの入力内容をチェックし問題が無ければ予約内容の確認ページへリダイレクトさせる
	@GetMapping("/houses/{id}/reservations/input")
	//GetMappingでURLにid情報を仕込むので@PathVariableを設定する　
	public String input(@PathVariable(name = "id") Integer id,
						@ModelAttribute @Validated ReservationInputForm reservationInputForm,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes,
						Model model)
	{
		//どの民宿を予約しているのかを区別するにはid情報を取得する　厳密にいえば、予約する民宿はどれか選んだ民宿情報を取得したい
		House house = houseRepository.getReferenceById(id);
		//予約する際に入力した宿泊人数を取得して変数に格納している
		Integer numberOfPeople = reservationInputForm.getNumberOfPeople();
		//houseがもっているフィールドの定員情報を取得して変数に格納している
		Integer capacity = house.getCapacity();
		
		//宿泊人数の入力値に値が入っていれば実行
		if (numberOfPeople != null) {
			//定員人数が定員以上だった場合実行
			if (!reservationService.isWithinCapacity(numberOfPeople, capacity) ) {
				//バリデーションメッセージを追加
				FieldError fieldError = new FieldError(bindingResult.getObjectName(), "numberOfPeople", "宿泊人数が定員を超えています");
				bindingResult.addError(fieldError);
			}
		}
		
		//入力内容に誤りがあった場合実行
		if (bindingResult.hasErrors()) {
			//houseの情報を持たせないと予約の情報がないため、画面を出力出来なくなる
			model.addAttribute("house", house);
			model.addAttribute("errorMessage", "予約内容に不備があります");
			return "houses/show";
					
		}
		
		//予約フォームの入力に誤りがない場合はReservationInputFormオブジェクトをそのままリダイレクト左記である予約内容の確認ページであるconfirmへ渡す
		redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);
		
		return "redirect:/houses/{id}/reservations/confirm";
	}
}
