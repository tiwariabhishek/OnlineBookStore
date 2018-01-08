package com.weblearning.bookstore.persistance;

import java.util.Date;
import java.util.List;

import com.weblearning.bookstore.exception.DAOLayerException;
import com.weblearning.bookstore.persistance.entities.SubscriptionPlan;
import org.hibernate.HibernateException;

import com.weblearning.bookstore.persistance.entities.ActiveSubscription;

public interface SubscriptionPlanDAO {

	public SubscriptionPlan getSubscriptionPlan(long planId)
			throws DAOLayerException;

	public void addSubscription(SubscriptionPlan subscriptionPlan) throws DAOLayerException;

	public List<SubscriptionPlan> getAllSubcriptions() throws DAOLayerException;

	public Date getPlanEndDate(String email) throws DAOLayerException;

	public void saveActiveSubscription(ActiveSubscription activeSubscription)
			throws DAOLayerException;

	public ActiveSubscription getActiveSubscriptionByEmail(String email)
			throws DAOLayerException;

	void myDAOLayerException(String message,
			HibernateException hibernateException) throws DAOLayerException;

}