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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserEntity")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId")
	private long userId;
	@Column(name = "password")
	private String password;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "roleId")
	private short roleId;
	@Column(name = "enabled")
	private boolean enabled;
	@Column(name = "balance")
	private int balance;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private UserDetails userdetails;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private ActiveSubscription activeSubscription;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private SubscriptionPlan subscriptionPlan;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private UserCart userCart;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private BookRecommendations bookRecommendations;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private WishList wishList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<UserHistory> userHistory = new HashSet<UserHistory>(0);

	public UserCart getUserCart() {
		return userCart;
	}

	public void setUserCart(UserCart userCart) {
		this.userCart = userCart;
	}

	public WishList getWishList() {
		return wishList;
	}

	public void setWishList(WishList wishList) {
		this.wishList = wishList;
	}

	public Set<UserHistory> getUserHistory() {
		return userHistory;
	}

	public void setUserHistory(Set<UserHistory> userHistory) {
		this.userHistory = userHistory;
	}

	public SubscriptionPlan getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public UserDetails getUserdetails() {
		return userdetails;
	}

	public void setUserdetails(UserDetails userdetails) {
		this.userdetails = userdetails;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ActiveSubscription getActiveSubscription() {
		return activeSubscription;
	}

	public void setActiveSubscription(ActiveSubscription activeSubscription) {
		this.activeSubscription = activeSubscription;
	}

	public BookRecommendations getBookRecommendations() {
		return bookRecommendations;
	}

	public void setBookRecommendations(BookRecommendations bookRecommendations) {
		this.bookRecommendations = bookRecommendations;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public short getRoleId() {
		return roleId;
	}

	public void setRoleId(short roleId) {
		this.roleId = roleId;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

}
