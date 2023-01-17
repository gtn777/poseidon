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

@Getter
@Setter
@Entity
@Table(name = "Trade")
public class Trade {

	public Trade() {
		super();
	}

	public Trade(String account, String type) {
		super();
		this.account = account;
		this.type = type;
	}

	@Id
	@Column(name = "TradeId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String account;

	@Column
	private String type;

	@Column
	private Double buyQuantity;

	@Column
	private Double sellQuantity;

	@Column
	private Double buyPrice;

	@Column
	private Double sellPrice;

	@Column
	private Timestamp tradeDate;

	@Column
	private String security;

	@Column
	private String status;

	@Column
	private String trader;

	@Column
	private String benchmark;

	@Column
	private String book;

	@Column
	private String creationName;

	@Column
	private Timestamp creationDate;

	@Column
	private String revisionName;

	@Column
	private Timestamp revisionDate;

	@Column
	private String dealName;

	@Column
	private String dealType;

	@Column
	private String sourceListId;

	@Column
	private String side;

}
