package com.example.samuraitravel.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.FavoriteService;

@Controller
public class FavoriteController {
	private final HouseRepository houseRepository;
	private final FavoriteRepository favoriteRepository;
	private final FavoriteService favoriteService;
	
	public FavoriteController(HouseRepository houseRepository, FavoriteRepository favoriteRepository, FavoriteService favoriteService) {
		this.houseRepository = houseRepository;
		this.favoriteRepository = favoriteRepository;
		this.favoriteService = favoriteService;
	}
	
	@PostMapping("/houses/{houseId}/favorite/create")
	public String create(@PathVariable(name = "houseId") Integer houseId,RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		House house = houseRepository.getReferenceById(houseId);
		User user = userDetailsImpl.getUser();
		
		favoriteService.create(house, user);
		redirectAttributes.addFlashAttribute("successMessage", "Ç®ãCÇ…ì¸ÇËìoò^ÇµÇ‹ÇµÇΩ");
		
		return "redirect:/houses/{houseId}";
	}
	
	@PostMapping("/houses/{houseId}/favorite/{favoriteId}/delete")
	public String delete(@PathVariable(name = "houseId") Integer houseId, @PathVariable(name = "favoriteId") Integer favoriteId, RedirectAttributes redirectAttributes) {
		favoriteRepository.deleteById(favoriteId);
		
		redirectAttributes.addFlashAttribute("successMessage", "Ç®ãCÇ…ì¸ÇËÇçÌèúÇµÇ‹ÇµÇΩ");
		
		return "redirect:/houses/{houseId}";
	}
	
}
