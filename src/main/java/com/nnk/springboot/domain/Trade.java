package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Trade")
public class Trade {

	public Trade() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Double buyQuantity) {
		this.buyQuantity = buyQuantity;
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
