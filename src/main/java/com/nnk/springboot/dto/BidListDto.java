package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BidListDto {

	private Integer bidListId;

	@NotBlank(message = "Account is mandatory.")
	@Size(min = 1, max = 20, message = "Type must be between 1 and 20 characters.")
	private String account;

	@NotBlank(message = "Type is mandatory.")
	@Size(min = 1, max = 20, message = "Type must be between 1 and 20 characters.")
	private String type;

	@NotNull(message = "Bid quantity is mandatory.")
	@Positive(message = "Bid quantity must be positive.")
	private Double bidQuantity;
}
