package com.example.samuraitravel.form;

import jakarta.validation.constraints.NotBlank;

public class ReviewRegisterForm {
	private Integer score;
	
	@NotBlank(message = "�R�����g����͂��Ă�������")
	private String content;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
