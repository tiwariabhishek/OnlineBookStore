package com.weblearning.bookstore.dto;


public class SubscriptionPlanDTO {
	
	private long planId;
	private int planDuration;
	private int planCost;
	private int maxBooks;

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

}
