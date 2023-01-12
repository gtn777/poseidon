package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "CurvePoint")
public class CurvePoint {

	public CurvePoint() {
		super();
	}

	public CurvePoint(Integer curveId, Double term, Double value) {
		super();
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "CurveId")
	private Integer curveId;

	@Column
	private Timestamp asOfDate;

	@Column
	private Double term;

	@Column
	private Double value;

	@Column
	private Timestamp creationDate;

}
