package com.weblearning.bookstore.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weblearning.bookstore.dto.ActiveSubscriptionDTO;
import com.weblearning.bookstore.dto.SubscriptionPlanDTO;
import com.weblearning.bookstore.dto.UserEntityDTO;
import com.weblearning.bookstore.exception.DAOLayerException;
import com.weblearning.bookstore.exception.ServiceLayerException;
import com.weblearning.bookstore.persistance.SubscriptionPlanDAO;
import com.weblearning.bookstore.persistance.UserDetailsDAO;
import com.weblearning.bookstore.persistance.entities.ActiveSubscription;
import com.weblearning.bookstore.persistance.entities.SubscriptionPlan;
import com.weblearning.bookstore.persistance.entities.UserEntity;
import com.weblearning.bookstore.persistance.entities.UserHistory;
import com.weblearning.bookstore.utils.PropertyConstants;
import com.weblearning.bookstore.utils.Utilities;
import com.weblearning.bookstore.utils.datatransfer.PersistanceToDTO;

@Service("subscriptionService")
public class SubscriptonServiceImpl implements SubscriptionService {

	private static final Logger LOGGER = Logger
			.getLogger(SubscriptonServiceImpl.class);

	@Autowired
	private SubscriptionPlanDAO subscriptionPlanDAO;

	@Autowired
	private UserDetailsDAO userDetailsDAO;

	@Override
	@Transactional
	public boolean uploadSubscriptionsXML(String filename)
			throws ServiceLayerException {
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			String fileSavedPath = System.getProperty("catalina.base");
			InputStream in = new FileInputStream(fileSavedPath + File.separator
					+ filename);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			SubscriptionPlan subscriptionPlan = null;
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().getLocalPart()
							.equals(PropertyConstants.SUBSCRIPTION)) {
						subscriptionPlan = new SubscriptionPlan();
						continue;
					}
					if (event.asStartElement().getName().getLocalPart()
							.equals(PropertyConstants.ID)) {
						event = eventReader.nextEvent();
						String id = event.asCharacters().getData();
						if (Utilities.checkForNumber(id)) {
							subscriptionPlan.setPlanId(Long.parseLong(id));
						}
						continue;
					}
					if (event.asStartElement().getName().getLocalPart()
							.equals(PropertyConstants.SUBDURATION)) {
						event = eventReader.nextEvent();
						subscriptionPlan.setPlanDuration(Integer.parseInt(event
								.asCharacters().getData()));
						continue;
					}

					if (event.asStartElement().getName().getLocalPart()
							.equals(PropertyConstants.SUBCOST)) {
						event = eventReader.nextEvent();
						subscriptionPlan.setPlanCost(Integer.parseInt(event
								.asCharacters().getData()));
						continue;
					}
					if (event.asStartElement().getName().getLocalPart()
							.equals(PropertyConstants.MAXBOOKS)) {
						event = eventReader.nextEvent();
						subscriptionPlan.setMaxBooks(Integer.parseInt(event
								.asCharacters().getData()));
						continue;
					}
					if (event.asStartElement().getName().getLocalPart()
							.equals(PropertyConstants.ACTION)) {
						event = eventReader.nextEvent();
						String action = event.asCharacters().getData();
						if (action.equalsIgnoreCase(PropertyConstants.ADD)) {
							subscriptionPlan.setPlanActive(true);
						} else if (action
								.equalsIgnoreCase(PropertyConstants.DELETE)) {
							subscriptionPlan.setPlanActive(false);
						} else {
							subscriptionPlan.setPlanActive(true);
						}
						continue;
					}
				}
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == (PropertyConstants.SUBSCRIPTION)) {
						subscriptionPlanDAO.addSubscription(subscriptionPlan);
					}
				}
			}
		} catch (NumberFormatException e) {
			LOGGER.error("Failed to parse number format.");
			return false;
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found.");
			return false;
		} catch (FactoryConfigurationError e) {
			LOGGER.error("Failed to configure XML factory.");
			return false;
		} catch (XMLStreamException e) {
			LOGGER.error("Failed to parse XML file.");
			return false;
		} catch (DAOLayerException e) {
			String message = "Failed to upload subscription xml.";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional
	public List<SubscriptionPlanDTO> getAllSubscriptions()
			throws ServiceLayerException {
		List<SubscriptionPlanDTO> subscriptionPlanDTOList = null;
		try {
			List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanDAO
					.getAllSubcriptions();
			subscriptionPlanDTOList = new ArrayList<SubscriptionPlanDTO>();
			for (SubscriptionPlan subscriptionPlan : subscriptionPlanList) {
				if (subscriptionPlan.isPlanActive()) {
					SubscriptionPlanDTO subscriptionPlanDTO = PersistanceToDTO
							.subscriptionPlanToDTO(subscriptionPlan);
					subscriptionPlanDTOList.add(subscriptionPlanDTO);
				}
			}
		} catch (DAOLayerException e) {
			String message = "Failed to fetch all subscription.";
			myServiceLayerException(message, e);
		}
		return subscriptionPlanDTOList;
	}

	@Override
	@Transactional
	public String changeSubscription(String email, long planId)
			throws ServiceLayerException {
		String message = null;
		try {
			UserEntity userEntity = userDetailsDAO.getUserByEmail(email);
			ActiveSubscription activeSubscription = userEntity
					.getActiveSubscription();
			SubscriptionPlan subscriptionPlan = subscriptionPlanDAO
					.getSubscriptionPlan(planId);
			SubscriptionPlan userSubscriptionPlan = subscriptionPlanDAO
					.getSubscriptionPlan(activeSubscription.getPlanId());
			int userMaxBooks = userSubscriptionPlan.getMaxBooks();
			int remBooks = activeSubscription.getRemBooks();
			activeSubscription.setPlanStartDate(new Date());
			activeSubscription.setPlanId(planId);
			activeSubscription.setPlanEndDate(Utilities.computeDate(new Date(),
					subscriptionPlan.getPlanDuration()));
			if (userMaxBooks == remBooks) {
				activeSubscription.setRemBooks(subscriptionPlan.getMaxBooks());
				userEntity.setBalance(subscriptionPlan.getPlanCost());
				userEntity.setEnabled(true);
				userDetailsDAO.updateUser(userEntity);
				message = PropertyConstants.SUBSCRIPTION_RENEW_SUCCESS;
			} else {
				message = PropertyConstants.SUBSCRIPTION_RENEW_FAILURE;
			}
		} catch (DAOLayerException e) {
			String messge = "Failed to change subscription plan.";
			myServiceLayerException(messge, e);
		}
		return message;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserEntityDTO> getAllUsersWithActiveSubscription()
			throws ServiceLayerException {
		List<UserEntityDTO> userEntityDTOs = null;
		try {
			List<UserEntity> userEntities = userDetailsDAO
					.getAllUsersWithActiveSubscription();
			userEntityDTOs = new ArrayList<UserEntityDTO>();
			for (UserEntity userEntity : userEntities) {
				if (userEntity.getRoleId() == PropertyConstants.ROLE_USER) {
					UserEntityDTO userEntityDTO = PersistanceToDTO
							.userEntityToDTO(userEntity);
					userEntityDTOs.add(userEntityDTO);
				}
			}
		} catch (DAOLayerException e) {
			String message = "Failed to fetch all users with active subscription.";
			myServiceLayerException(message, e);
		}
		return userEntityDTOs;
	}

	@Override
	@Transactional(readOnly = true)
	public SubscriptionPlanDTO getSubscriptionPlan(long planId)
			throws ServiceLayerException {
		SubscriptionPlanDTO subscriptionPlanDTO = null;
		try {
			SubscriptionPlan subscriptionPlan = subscriptionPlanDAO
					.getSubscriptionPlan(planId);
			subscriptionPlanDTO = PersistanceToDTO
					.subscriptionPlanToDTO(subscriptionPlan);
		} catch (DAOLayerException e) {
			String message = "Failed to fetch subscription plan.";
			myServiceLayerException(message, e);
		}
		return subscriptionPlanDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ActiveSubscriptionDTO> getUserSubscriptionHistory(String email)
			throws ServiceLayerException {
		List<ActiveSubscriptionDTO> activeSubscriptionDTOs = null;
		try {
			List<UserHistory> userHistories = userDetailsDAO
					.getUserHistory(email);
			activeSubscriptionDTOs = new ArrayList<ActiveSubscriptionDTO>();
			for (UserHistory userHistory : userHistories) {
				ActiveSubscriptionDTO activeSubscriptionDTO = PersistanceToDTO
						.userHistoryToDTO(userHistory);
				activeSubscriptionDTOs.add(activeSubscriptionDTO);
			}
		} catch (DAOLayerException e) {
			String message = "Failed to fetch user subscription history.";
			myServiceLayerException(message, e);
		}
		return activeSubscriptionDTOs;
	}

	@Override
	public void myServiceLayerException(String message,
			DAOLayerException daoLayerException) throws ServiceLayerException {
		LOGGER.error(message, daoLayerException);
		throw new ServiceLayerException(message);
	}
}
