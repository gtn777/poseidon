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
@Table(name = "BidList")
public class BidList {

	public BidList() {
		super();
	}
	
	public BidList(String account, String type, Double bidQuantity) {
		super();
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

	@Id
	@Column(name = "BidListId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bidListId;

	@Column
	private String account;

	@Column
	private String type;

	@Column
	private Double bidQuantity;

	@Column
	private Double askQuantity;

	@Column
	private Double bid;

	@Column
	private Double ask;

	@Column
	private Timestamp bidListDate;

	@Column
	private String commentary;

	@Column
	private String security;

	@Column
	private String status;

	@Column
	private String trader;

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
