package com.weblearning.bookstore.persistance.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "ActiveSubscription")
public class ActiveSubscription {

	@Id
	@GeneratedValue(generator = "newGenerator")
	@GenericGenerator(name = "newGenerator", strategy = "foreign", parameters = { @Parameter(value = "user", name = "property") })
	private long userId;

	@Column(name = "planId")
	private long planId;

	@Column(name = "remBooks")
	private int remBooks;

	@Column(name = "planStartDate")
	private Date planStartDate;

	@Column(name = "planEndDate")
	private Date planEndDate;

	@Column(name = "detailsAddedToHistory")
	private boolean detailsAddedToHistory;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private UserEntity user;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ActiveBookRequests> activeRequests = new HashSet<ActiveBookRequests>();

	public long getPlanId() {
		return planId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public Set<ActiveBookRequests> getActiveRequests() {
		return activeRequests;
	}

	public void setActiveRequests(Set<ActiveBookRequests> activeRequests) {
		this.activeRequests = activeRequests;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getRemBooks() {
		return remBooks;
	}

	public void setRemBooks(int remBooks) {
		this.remBooks = remBooks;
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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public boolean isDetailsAddedToHistory() {
		return detailsAddedToHistory;
	}

	public void setDetailsAddedToHistory(boolean detailsAddedToHistory) {
		this.detailsAddedToHistory = detailsAddedToHistory;
	}

	// @Override
	// public String toString() {
	// return "ActiveSubscription [userId=" + userId + ", planId=" + planId
	// + ", planStartDate=" + planStartDate + ", planEndDate="
	// + planEndDate + ", detailsAddedToHistory="
	// + detailsAddedToHistory + ", user=" + user
	// + ", activeRequests=" + activeRequests + "]";
	// }

}
