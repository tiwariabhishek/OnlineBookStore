package com.weblearning.bookstore.persistance;

import java.util.List;

import com.weblearning.bookstore.exception.DAOLayerException;
import com.weblearning.bookstore.persistance.entities.ActiveBookRequests;
import com.weblearning.bookstore.persistance.entities.UserEntity;
import com.weblearning.bookstore.persistance.entities.UserHistory;
import com.weblearning.bookstore.utils.PropertyConstants;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.weblearning.bookstore.persistance.entities.ActiveSubscription;

@Repository
public class UserDetailsDAOImpl implements UserDetailsDAO {

	private static final Logger LOGGER = Logger
			.getLogger(UserDetailsDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserHistory> getUserHistory(String email)
			throws DAOLayerException {
		List<UserHistory> userHistories = null;
		try {
			String hql = "select userentity.userHistory FROM UserEntity userentity WHERE userentity.email='"
					+ email + "'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			userHistories = query.list();
		} catch (HibernateException hibernateException) {
			String message = "Failed to fetch userhistory from database";
			myDAOLayerException(message, hibernateException);
		}
		return userHistories;
	}

	@Override
	public void registerUser(UserEntity userEntity) throws DAOLayerException {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(userEntity);
		} catch (HibernateException hibernateException) {
			String message = "Failed to register user";
			myDAOLayerException(message, hibernateException);
		}
	}

	@Override
	public void updateUser(UserEntity userEntity) {
		try {
			sessionFactory.getCurrentSession().update(userEntity);
		} catch (HibernateException hibernateException) {

		}
	}

	@Override
	public Boolean getUserStatus(String email) throws DAOLayerException {
		Boolean enabled = null;
		try {
			String hql = "select enabled FROM UserEntity WHERE email='" + email
					+ "'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			enabled = (boolean) query.list().get(0);
		} catch (HibernateException hibernateException) {
			String message = "Failed to get User Status";
			myDAOLayerException(message, hibernateException);
		} catch (Exception e) {
			return null;
		}
		return enabled;
	}

	@Override
	public void saveBookRequest(ActiveSubscription activeSubscription)
			throws DAOLayerException {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(activeSubscription);
		} catch (HibernateException hibernateException) {
			String message = "Failed to save Book Request";
			myDAOLayerException(message, hibernateException);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllUsersWithActiveSubscription()
			throws DAOLayerException {
		List<UserEntity> userList = null;
		try {
			String hql = "From UserEntity WHERE enabled=" + true;
			userList = sessionFactory.getCurrentSession().createQuery(hql)
					.list();
		} catch (HibernateException hibernateException) {
			String message = "Failed to get all users with active subscription";
			myDAOLayerException(message, hibernateException);
		}
		return userList;
	}

	@Override
	public UserEntity getUserByEmail(String email) throws DAOLayerException {
		UserEntity userEntity = null;
		try {
			String hql = "FROM UserEntity WHERE email='" + email + "'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			userEntity = (UserEntity) query.list().get(0);
		} catch (HibernateException hibernateException) {
			String message = "Failed to get user by email";
			myDAOLayerException(message, hibernateException);
		}
		return userEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllUserEmails() throws DAOLayerException {
		List<String> usersEmail = null;
		try {
			String hql = "select email FROM UserEntity WHERE roleId="
					+ PropertyConstants.ROLE_USER;
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			usersEmail = query.list();
		} catch (HibernateException hibernateException) {
			String message = "Failed to get all user emails";
			myDAOLayerException(message, hibernateException);
		}
		return usersEmail;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActiveBookRequests> getAllUserRequests(String email)
			throws DAOLayerException {
		List<ActiveBookRequests> activeBookRequests = null;
		try {
			String hql = "select userentity.activeSubscription.activeRequests FROM UserEntity userentity WHERE userentity.email='"
					+ email + "'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			activeBookRequests = query.list();
		} catch (HibernateException hibernateException) {
			String message = "Failed to get all user requests.";
			myDAOLayerException(message, hibernateException);
		}
		return activeBookRequests;
	}

	@Override
	public void updateActiveBookRequests(ActiveBookRequests activeBookRequest)
			throws DAOLayerException {
		try {
			sessionFactory.getCurrentSession().update(activeBookRequest);
		} catch (HibernateException hibernateException) {
			String message = "Failed to update active book requests.";
			myDAOLayerException(message, hibernateException);
		}
	}

	@Override
	public void myDAOLayerException(String message,
			HibernateException hibernateException) throws DAOLayerException {
		LOGGER.error(message, hibernateException);
		throw new DAOLayerException(message);
	}

}
