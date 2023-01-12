package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RuleNameDto {
	public RuleNameDto() {
		super();
	}

	private Integer id;

	@NotBlank(message = "Name is mandatory.")
	@Size(min = 1, max = 20, message = "Account must be between 1 and 20 characters.")
	private String name;

	@NotBlank(message = "Description is mandatory.")
	@Size(min = 1, max = 20, message = "Account must be between 1 and 20 characters.")
	private String description;

	@NotBlank(message = "Json is mandatory.")
	@Size(min = 1, max = 20, message = "Account must be between 1 and 20 characters.")
	private String json;

	@NotBlank(message = "Template is mandatory.")
	@Size(min = 1, max = 20, message = "Account must be between 1 and 20 characters.")
	private String template;

	@NotBlank(message = "SqlStr is mandatory.")
	@Size(min = 1, max = 20, message = "Account must be between 1 and 20 characters.")
	private String sqlStr;

	@NotBlank(message = "SqlPart is mandatory.")
	@Size(min = 1, max = 20, message = "Account must be between 1 and 20 characters.")
	private String sqlPart;
}
