package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.ReviewService;

@Controller
@RequestMapping("/houses/{houseId}/review")
public class ReviewController {
	private final HouseRepository houseRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	
	public ReviewController(HouseRepository houseRepository, ReviewRepository reviewRepository, ReviewService reviewService) {
		this.houseRepository = houseRepository;
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
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
	
	@GetMapping("/register")
	public String register(@PathVariable(name = "houseId") Integer houseId, Model model) {
		House house = houseRepository.getReferenceById(houseId);
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
		model.addAttribute("house", house);
		return "review/register";
	}
	
	@PostMapping("/create")
	public String create(@PathVariable(name = "houseId") Integer houseId, 
						@ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm, 
						BindingResult bindingResult, 
						RedirectAttributes redirevtattributes, 
						@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						Model model) 
		{
		House house = houseRepository.getReferenceById(houseId);
		User user = userDetailsImpl.getUser();
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("house", house);
			return "review/register";
		}
		
		
		reviewService.create(house, user, reviewRegisterForm);
		redirevtattributes.addFlashAttribute("successMessage", "ÉåÉrÉÖÅ[ÇìäçeÇµÇ‹ÇµÇΩ");
		
		return "redirect:/houses/{houseId}";
	}
}
