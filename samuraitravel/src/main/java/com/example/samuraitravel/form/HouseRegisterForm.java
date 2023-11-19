package com.example.samuraitravel.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HouseRegisterForm {
	@NotBlank(message = "–¯h–¼‚ğ“ü—Í‚µ‚Ä‚­‚¾‚³‚¢")
	private String name;
	
	private MultipartFile imageFile;
	
	@NotBlank(message = "à–¾‚ğ“ü—Í‚µ‚Ä‚­‚¾‚³‚¢")
	private String description;
	
	@NotNull(message = "h”‘—¿‹à‚ğ“ü—Í‚µ‚Ä‚­‚¾‚³‚¢")
	@Min(value = 1, message = "h”‘—¿‹à‚Í1‰~ˆÈã‚Éİ’è‚µ‚Ä‚­‚¾‚³‚¢")
	private Integer price;
	
	@NotNull(message = "’èˆõ‚ğ“ü—Í‚µ‚Ä‚­‚¾‚³‚¢")
	@Min(value = 1, message = "’èˆõ‚Í1lˆÈã‚Éİ’è‚µ‚Ä‚­‚¾‚³‚¢")
	private Integer capacity;
	
	@NotBlank(message = "—X•Ö”Ô†‚ğ“ü—Í‚µ‚Ä‚­‚¾‚³‚¢")
	private String postalCode;
	
	@NotBlank(message = "ZŠ‚ğ“ü—Í‚µ‚Ä‚­‚¾‚³‚¢")
	private String address;
	
	@NotBlank(message = "“d˜b”Ô†‚ğ“ü—Í‚µ‚Ä‚­‚¾‚³‚¢")
	private String phoneNumber;

}
