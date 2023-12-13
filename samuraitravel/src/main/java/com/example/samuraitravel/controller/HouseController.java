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
@RequestMapping("/houses")
public class HouseController {
	private final HouseRepository houseRepository;
	
	public HouseController(HouseRepository houseRepository) {
		this.houseRepository = houseRepository;
	}
	
	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
						@RequestParam(name = "address", required = false) String address,
						@RequestParam(name = "price", required = false) Integer price,
						@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC)Pageable pageable,
						Model model
						) {
		Page<House> housePage = null;
		
		//���h���܂��͖ړI�n�Ō�������t�H�[��
		if (keyword != null && !keyword.isEmpty()) {
			housePage = houseRepository.findByNameLikeOrAddressLike("%" + keyword + "%", "%" + address + "%", pageable);
		} else if (address != null && !address.isEmpty()) {
			//�G���A�i�s���{���j�Ō�������t�H�[��
			housePage = houseRepository.findByAddressLike("%" + address + "%", pageable);
		} else if (price != null) {
			//1��������̗\�Z�i�`�~�ȓ��j�Ō�������t�H�[��
			housePage = houseRepository.findByPriceLessThanEqual(price, pageable);
		} else {
			housePage = houseRepository.findAll(pageable);
		}
		
		model.addAttribute("housePage", housePage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("address", address);
		model.addAttribute("price", price);

		return "/houses/index";
	}
	
}
