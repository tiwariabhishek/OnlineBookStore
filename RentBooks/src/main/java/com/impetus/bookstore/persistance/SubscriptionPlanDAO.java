package com.impetus.bookstore.persistance;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.impetus.bookstore.exception.DAOLayerException;
import com.impetus.bookstore.persistance.entities.ActiveSubscription;
import com.impetus.bookstore.persistance.entities.SubscriptionPlan;

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