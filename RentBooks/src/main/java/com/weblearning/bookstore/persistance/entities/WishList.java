package com.weblearning.bookstore.persistance.entities;

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
@Table(name = "WishList")
public class WishList {

	@Id
	@GeneratedValue(generator = "newGenerator")
	@GenericGenerator(name = "newGenerator", strategy = "foreign", parameters = { @Parameter(value = "user", name = "property") })
	@Column(name = "userId")
	private long userId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private UserEntity user;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<RequestBook> bookIds = new HashSet<RequestBook>(0);

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Set<RequestBook> getBookIds() {
		return bookIds;
	}

	public void setBookIds(Set<RequestBook> bookIds) {
		this.bookIds = bookIds;
	}

}
