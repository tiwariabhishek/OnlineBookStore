package com.weblearning.bookstore.persistance;

import java.util.Date;
import java.util.List;

import com.weblearning.bookstore.exception.DAOLayerException;
import com.weblearning.bookstore.persistance.entities.SubscriptionPlan;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.weblearning.bookstore.persistance.entities.ActiveSubscription;

@Repository
public class SubscriptionPlanDAOImpl implements SubscriptionPlanDAO {

	private static final Logger LOGGER = Logger
			.getLogger(SubscriptionPlanDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addSubscription(SubscriptionPlan subscriptionPlan)
			throws DAOLayerException {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(subscriptionPlan);
		} catch (HibernateException e) {
			String message = "Failed to add subscription to the database.";
			myDAOLayerException(message, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubscriptionPlan> getAllSubcriptions() throws DAOLayerException {
		List<SubscriptionPlan> subscriptionPlans = null;
		try {
			subscriptionPlans = (List<SubscriptionPlan>) sessionFactory
					.getCurrentSession().createCriteria(SubscriptionPlan.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (HibernateException e) {
			String message = "Failed to fetch all subscription plans.";
			myDAOLayerException(message, e);
		}
		return subscriptionPlans;
	}

	@Override
	public SubscriptionPlan getSubscriptionPlan(long planId)
			throws DAOLayerException {
		SubscriptionPlan subscriptionPlan = null;
		try {
			String hql = "FROM SubscriptionPlan WHERE planId=" + planId;
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			subscriptionPlan = (SubscriptionPlan) query.list().get(0);
		} catch (HibernateException e) {
			String message = "Failed to fetch subscription plan.";
			myDAOLayerException(message, e);
		}
		return subscriptionPlan;
	}

	@Override
	public Date getPlanEndDate(String email) throws DAOLayerException {
		Date planEndDate = null;
		try {
			String hql = "select userentity.activeSubscription.planEndDate FROM UserEntity userentity WHERE userentity.email='"
					+ email + "'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			planEndDate = (Date) query.list().get(0);
		} catch (HibernateException e) {
			String message = "Failed to fetch plan end date.";
			myDAOLayerException(message, e);
		}
		return planEndDate;
	}

	@Override
	public ActiveSubscription getActiveSubscriptionByEmail(String email)
			throws DAOLayerException {
		ActiveSubscription activeSubscription = null;
		try {
			String hql = "select userentity.activeSubscription FROM UserEntity userentity WHERE userentity.email='"
					+ email + "'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			activeSubscription = (ActiveSubscription) query.list().get(0);
		} catch (HibernateException e) {
			String message = "Failed to fetch active subscription by email.";
			myDAOLayerException(message, e);
		}
		return activeSubscription;
	}

	@Override
	public void saveActiveSubscription(ActiveSubscription activeSubscription) throws DAOLayerException {
		try {
			sessionFactory.getCurrentSession().update(activeSubscription);
		} catch (HibernateException e) {
			String message = "Failed to save active subscription.";
			myDAOLayerException(message, e);
		}
	}

	@Override
	public void myDAOLayerException(String message,
			HibernateException hibernateException) throws DAOLayerException {
		LOGGER.error(message, hibernateException);
		throw new DAOLayerException(message);
	}
}
