package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BidListDto {

	public BidListDto() {
		super();
	}

	public BidListDto(String account, String type, Double bidQuantity) {
		super();
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

	public BidListDto(Integer bidListId, String account, String type, Double bidQuantity) {
		super();
		this.bidListId = bidListId;
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

	private Integer bidListId;

	@NotBlank(message = "Account is mandatory.")
	@Size(min = 1, max = 20, message = "Account must be between 1 and 20 characters.")
	private String account;

	@NotBlank(message = "Type is mandatory.")
	@Size(min = 1, max = 20, message = "Type must be between 1 and 20 characters.")
	private String type;

	@NotNull(message = "Bid quantity is mandatory.")
	@Positive(message = "Bid quantity must be positive.")
	private Double bidQuantity;

}
