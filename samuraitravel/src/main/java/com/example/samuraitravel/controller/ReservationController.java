package com.example.samuraitravel.controller;

import java.time.LocalDate;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Reservation;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReservationInputForm;
import com.example.samuraitravel.form.ReservationRegisterForm;
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
	
	//�\��t�H�[���̑��M��(/houses/{id}/reservations/input)��S������input()���\�b�h���`����
	//�����c�\��t�H�[���̓��͓��e���`�F�b�N����肪������Η\����e�̊m�F�y�[�W�փ��_�C���N�g������
	@GetMapping("/houses/{id}/reservations/input")
	//GetMapping��URL��id�����d���ނ̂�@PathVariable��ݒ肷��@
	public String input(@PathVariable(name = "id") Integer id,
						@ModelAttribute @Validated ReservationInputForm reservationInputForm,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes,
						Model model)
	{
		//�ǂ̖��h��\�񂵂Ă���̂�����ʂ���ɂ�id�����擾����@�����ɂ����΁A�\�񂷂閯�h�͂ǂꂩ�I�񂾖��h�����擾������
		House house = houseRepository.getReferenceById(id);
		//�\�񂷂�ۂɓ��͂����h���l�����擾���ĕϐ��Ɋi�[���Ă���
		Integer numberOfPeople = reservationInputForm.getNumberOfPeople();
		//house�������Ă���t�B�[���h�̒�������擾���ĕϐ��Ɋi�[���Ă���
		Integer capacity = house.getCapacity();
		
		//�h���l���̓��͒l�ɒl�������Ă���Ύ��s
		if (numberOfPeople != null) {
			//����l��������ȏゾ�����ꍇ���s
			if (!reservationService.isWithinCapacity(numberOfPeople, capacity) ) {
				//�o���f�[�V�������b�Z�[�W��ǉ�
				FieldError fieldError = new FieldError(bindingResult.getObjectName(), "numberOfPeople", "�h���l��������𒴂��Ă��܂�");
				bindingResult.addError(fieldError);
			}
		}
		
		//���͓��e�Ɍ�肪�������ꍇ���s
		if (bindingResult.hasErrors()) {
			//house�̏����������Ȃ��Ɨ\��̏�񂪂Ȃ����߁A��ʂ��o�͏o���Ȃ��Ȃ�
			model.addAttribute("house", house);
			model.addAttribute("errorMessage", "�\����e�ɕs��������܂�");
			return "houses/show";
					
		}
		
		//�\��t�H�[���̓��͂Ɍ�肪�Ȃ��ꍇ��ReservationInputForm�I�u�W�F�N�g�����̂܂܃��_�C���N�g���L�ł���\����e�̊m�F�y�[�W�ł���confirm�֓n��
		redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);
		
		return "redirect:/houses/{id}/reservations/confirm";
	}
	
	//input()���\�b�h�̃��_�C���N�g��Ɏw�肵�����\�b�h���`����
	//input()���\�b�h����n���ꂽReservationInputForm�I�u�W�F�N�g�����ReservationRegisterForm�I�u�W�F�N�g�𐶐����A�\����e�̊m�F�y�[�W��\������
	@GetMapping("/houses/{id}/reservations/confirm")
	public String confirm(@PathVariable(name = "id") Integer id,
						  @ModelAttribute ReservationInputForm reservationInputForm,
						  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						  Model model)
	{
		House house = houseRepository.getReferenceById(id);
		User user = userDetailsImpl.getUser();
		
		//�`�F�b�N�C�����ƃ`�F�b�N�A�E�g�����擾����
		LocalDate checkinDate = reservationInputForm.getCheckinDate();
		LocalDate checkoutDate = reservationInputForm.getCheckoutDate();
		
		//�h���������v�Z����
		Integer price = house.getPrice();
		Integer amount = reservationService.calculateAmount(checkinDate, checkoutDate, price);
		
		ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(house.getId(),user.getId(),checkinDate.toString(), checkoutDate.toString(), reservationInputForm.getNumberOfPeople(), amount);
		
		model.addAttribute("house", house);
		model.addAttribute("reservationRegisterForm", reservationRegisterForm);
		
		return "reservations/confirm";
	}
	
	@PostMapping("/houses/{id}/reservations/create")
	public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {
		reservationService.create(reservationRegisterForm);
		
		return "redirect:/reservations?reserved";
	}
}
