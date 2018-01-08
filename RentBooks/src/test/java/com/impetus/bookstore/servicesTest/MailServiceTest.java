package com.impetus.bookstore.servicesTest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.impetus.bookstore.configuration.AppConfig;
import com.impetus.bookstore.services.MailService;
import com.impetus.bookstore.utils.PropertyConstants;

@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class MailServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	MailService mailService;

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
	public void testSendEmailNotifications() {
		assertEquals(PropertyConstants.SUCCESS,
				mailService.sendEmailNotifications("abhishek@gmail.com",
						"Abhishek", "Test", "Hello User!"));
	}

}
