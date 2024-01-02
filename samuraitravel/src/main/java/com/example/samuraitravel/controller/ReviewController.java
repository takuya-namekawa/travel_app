package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;

@Controller
@RequestMapping("/houses/{houseId}/review")
public class ReviewController {
	private final HouseRepository houseRepository;
	private final ReviewRepository reviewRepository;
	
	public ReviewController(HouseRepository houseRepository, ReviewRepository reviewRepository) {
		this.houseRepository = houseRepository;
		this.reviewRepository = reviewRepository;
	}
	
	@GetMapping
	public String index(@PathVariable(name = "houseId") Integer houseId,
						@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
						Model model) 
	{

		House house = houseRepository.getReferenceById(houseId);
		Page<Review> reviewPage = reviewRepository.findByHouseOrderByCreatedAtDesc(house, pageable);
		
		model.addAttribute("house", house);
		model.addAttribute("reviewPage", reviewPage);
		
		return "review/index";
	}
}
