package com.weblearning.bookstore.servicesTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.weblearning.bookstore.configuration.AppConfig;
import com.weblearning.bookstore.exception.ServiceLayerException;
import com.weblearning.bookstore.services.SubscriptionService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class SubscriptionServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
    SubscriptionService subscriptionService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUploadSubscriptionXML() throws ServiceLayerException {
		assertFalse(subscriptionService.uploadSubscriptionsXML("sub.xml"));
	}

	@Test
	public void testGetAllSubscriptions() throws ServiceLayerException {
		assertNotNull(subscriptionService.getAllSubscriptions());
	}

	@Test
	public void testChangeSubscriptions() throws ServiceLayerException {
		assertNotNull(subscriptionService.changeSubscription(
				"abhishek@gmail.com", 1));
	}

	@Test
	public void testGetAllUsersWithActiveSubscription()
			throws ServiceLayerException {
		assertNotNull(subscriptionService.getAllUsersWithActiveSubscription());
	}

	@Test
	public void testGetSubscriptionPlan() throws ServiceLayerException {
		assertNotNull(subscriptionService.getSubscriptionPlan(1));
	}

	@Test
	public void testGetUserSubscriptionHistory() throws ServiceLayerException {
		assertNotNull(subscriptionService
				.getUserSubscriptionHistory("abhishek@gmail.com"));
	}
}
