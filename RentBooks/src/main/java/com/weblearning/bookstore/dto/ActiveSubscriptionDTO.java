package com.weblearning.bookstore.dto;

public class ActiveSubscriptionDTO {

	private long historyId;
	private long planId;
	private String planStartDate;
	private String planEndDate;
	private int remBooks;
	private int remDays;

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

	public String getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(String planStartDate) {
		this.planStartDate = planStartDate;
	}

	public String getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}

	public int getRemBooks() {
		return remBooks;
	}

	public void setRemBooks(int remBooks) {
		this.remBooks = remBooks;
	}

	public int getRemDays() {
		return remDays;
	}

	public void setRemDays(int remDays) {
		this.remDays = remDays;
	}

}
