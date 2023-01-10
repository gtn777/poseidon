package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class RatingDto {

	public RatingDto() {
		super();
	}

	public RatingDto(String moodysRating, String sandPRating, String fitchRating,
			Integer orderNumber) {
		super();
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

	public RatingDto(Integer id, String moodysRating, String sandPRating, String fitchRating,
			Integer orderNumber) {
		super();
		this.id = id;
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMoodysRating() {
		return moodysRating;
	}

	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}

	public String getSandPRating() {
		return sandPRating;
	}

	public void setSandPRating(String sandPRating) {
		this.sandPRating = sandPRating;
	}

	public String getFitchRating() {
		return fitchRating;
	}

	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	private Integer id;

	@NotBlank(message = "MoodysRating is mandatory.")
	@Size(min = 1, max = 20, message = "MoodysRating must be between 1 and 20 characters.")
	private String moodysRating;

	@NotBlank(message = "SandPRating is mandatory.")
	@Size(min = 1, max = 20, message = "SandPRating must be between 1 and 20 characters.")
	private String sandPRating;

	@NotBlank(message = "FitchPRating is mandatory.")
	@Size(min = 1, max = 20, message = "FitchRating must be between 1 and 20 characters.")
	private String fitchRating;

	@NotNull(message = "OrderNumber is mandatory.")
	@Positive(message = "OrderNumber must be positive.")
	private Integer orderNumber;

}
