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
import com.example.samuraitravel.form.ReviewEditForm;
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
		redirevtattributes.addFlashAttribute("successMessage", "レビューを投稿しました");
		
		return "redirect:/houses/{houseId}";
	}
	
	//変更をかけるにはレビューのidを対象にして、カラム情報を取得する必要がある　カラム情報はエンティティに入っているのでエンティティの値をセットしないとcreateした時の情報はフォームに入ってこない	
	@GetMapping("/{reviewId}/edit")
	public String edit(@PathVariable(name = "reviewId") Integer reviewId,
					   @PathVariable(name = "houseId") Integer houseId,
					    Model model)
	{
		House house = houseRepository.getReferenceById(houseId);
		Review review = reviewRepository.getReferenceById(reviewId);
		
		ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(), review.getScore(), review.getContent());
		
		model.addAttribute("house", house);
		model.addAttribute("review", review);
		model.addAttribute("reviewEditForm", reviewEditForm);
		
		return "review/edit";
	}
	
	
	@PostMapping("/{reviewId}/update")
	public String update(@ModelAttribute @Validated ReviewEditForm reviewEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable(name = "reviewId") Integer reviewId, @PathVariable(name = "houseId") Integer houseId, Model model) {
		House house = houseRepository.getReferenceById(houseId);
		Review review = reviewRepository.getReferenceById(reviewId);
		if (bindingResult.hasErrors()) {
			model.addAttribute("house",house);
			model.addAttribute("review",review);
			return "review/edit";
		}
		
		
		reviewService.update(reviewEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを更新しました");
		
		return "redirect:/houses/{houseId}";
	
	}
	
	
	@PostMapping("{revireId}/delete")
	public String delete(@PathVariable(name = "revireId") Integer reviewId, @PathVariable(name = "houseId") Integer houseId, RedirectAttributes redirectAttributes) {
		reviewRepository.deleteById(reviewId);
		
		redirectAttributes.addFlashAttribute("successMessage","レビューを削除しました");
		
		return "redirect:/houses/{houseId}";
	}
	
	
	//入力された情報が取れなかった
//	@GetMapping("/edit")
//	public String edit(@PathVariable(name = "houseId") Integer houseId, ReviewRegisterForm reviewRegisterForm, ReviewEditForm reviewEditForm, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
//		House house = houseRepository.getReferenceById(houseId);
//		User user = userDetailsImpl.getUser();
//		
//		reviewEditForm = new ReviewEditForm(house, user, reviewRegisterForm.getContent(), reviewRegisterForm.getScore());
//		model.addAttribute("house", house);
//		model.addAttribute("reviewEditForm", reviewEditForm);
//		
//		return "review/edit";
//	
//	}
}
