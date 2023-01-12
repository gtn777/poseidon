package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getSqlPart() {
		return sqlPart;
	}

	public void setSqlPart(String sqlPart) {
		this.sqlPart = sqlPart;
	}
}
