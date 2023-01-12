package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeDto {

	public TradeDto() {
		super();
	}

	private Integer id;

	@NotBlank(message = "Account is mandatory.")
	@Size(min = 1, max = 20, message = "Account must be between 1 and 20 characters.")
	private String account;

	@NotBlank(message = "Type is mandatory.")
	@Size(min = 1, max = 20, message = "Type must be between 1 and 20 characters.")
	private String type;

	@NotNull(message = "Value is mandatory.")
	@Positive(message = "Value must be positive.")
	private Double buyQuantity;

}
