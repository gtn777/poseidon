package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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

	public Integer getBidListId() {
		return bidListId;
	}

	public void setBidListId(Integer bidListId) {
		this.bidListId = bidListId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBidQuantity() {
		return bidQuantity;
	}

	public void setBidQuantity(Double bidQuantity) {
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
