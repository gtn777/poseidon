package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BidList")
public class BidList {

	public BidList() {
		super();
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

	public Integer getBidListId() {
		return bidListId;
	}

	public void setBidListId(Integer id) {
		this.bidListId = id;
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

	public Double getAskQuantity() {
		return askQuantity;
	}

	public void setAskQuantity(Double askQuantity) {
		this.askQuantity = askQuantity;
	}

	public Double getBidDate() {
		return bid;
	}

	public void setBidDate(Double bidDate) {
		this.bid = bidDate;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public Timestamp getBidListDate() {
		return bidListDate;
	}

	public void setBidListDate(Timestamp bidListDate) {
		this.bidListDate = bidListDate;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getRevisionName() {
		return revisionName;
	}

	public void setRevisionName(String revisionName) {
		this.revisionName = revisionName;
	}

	public Timestamp getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Timestamp revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getSourceListId() {
		return sourceListId;
	}

	public void setSourceListId(String sourceListId) {
		this.sourceListId = sourceListId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

}
