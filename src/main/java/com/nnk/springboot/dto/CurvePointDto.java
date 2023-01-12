package com.nnk.springboot.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CurvePointDto {

	public CurvePointDto() {
		super();
	}

	public CurvePointDto(Integer curveId, Double term, Double value) {
		super();
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}

	public CurvePointDto(Integer id, Integer curveId, Double term, Double value) {
		super();
		this.id = id;
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}

	private Integer id;

	@NotNull(message = "CurvePointId is mandatory.")
	@Positive(message = "CurvePointId must be positive.")
	private Integer curveId;

	@NotNull(message = "Term is mandatory.")
	@Positive(message = "Term must be positive.")
	private Double term;

	@NotNull(message = "Value is mandatory.")
	@Positive(message = "Value must be positive.")
	private Double value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurveId() {
		return curveId;
	}

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}

	public Double getTerm() {
		return term;
	}

	public void setTerm(Double term) {
		this.term = term;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
