package com.impetus.bookstore.persistance.entities;

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
@Table(name = "subscriptionplan")
public class SubscriptionPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "planId")
	private long planId;
	@Column(name = "planDuration")
	private int planDuration;
	@Column(name = "planCost")
	private int planCost;
	@Column(name = "maxBooks")
	private int maxBooks;
	@Column(name = "planStatus")
	private boolean planActive;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "subscriptionPlan", fetch = FetchType.LAZY)
	private Set<UserEntity> user = new HashSet<UserEntity>(0);

	public Set<UserEntity> getUser() {
		return user;
	}

	public void setUser(Set<UserEntity> user) {
		this.user = user;
	}

	public long getPlanId() {
		return planId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public int getPlanDuration() {
		return planDuration;
	}

	public void setPlanDuration(int planDuration) {
		this.planDuration = planDuration;
	}

	public int getPlanCost() {
		return planCost;
	}

	public void setPlanCost(int planCost) {
		this.planCost = planCost;
	}

	public int getMaxBooks() {
		return maxBooks;
	}

	public void setMaxBooks(int maxBooks) {
		this.maxBooks = maxBooks;
	}

	public boolean isPlanActive() {
		return planActive;
	}

	public void setPlanActive(boolean planActive) {
		this.planActive = planActive;
	}

}
