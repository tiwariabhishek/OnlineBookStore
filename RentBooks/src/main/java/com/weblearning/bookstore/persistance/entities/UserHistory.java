package com.weblearning.bookstore.persistance.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "UserHistory")
public class UserHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long historyId;

	@Column(name = "planId")
	private long planId;

	@Column(name = "planStartDate")
	private Date planStartDate;

	@Column(name = "planEndDate")
	private Date planEndDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<BookReqHistory> bookReqHisory = new HashSet<BookReqHistory>(0);

	public long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(long historyId) {
		this.historyId = historyId;
	}

	public long getPlanId() {
		return planId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Date getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	public Set<BookReqHistory> getBookReqHisory() {
		return bookReqHisory;
	}

	public void setBookReqHisory(Set<BookReqHistory> bookReqHisory) {
		this.bookReqHisory = bookReqHisory;
	}

}
