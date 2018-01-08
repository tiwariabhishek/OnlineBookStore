package com.impetus.bookstore.servicesTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.impetus.bookstore.configuration.AppConfig;
import com.impetus.bookstore.dto.UserEntityDTO;
import com.impetus.bookstore.exception.ServiceLayerException;
import com.impetus.bookstore.services.UserService;

@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class UserServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	UserService userService;

	@Before
	public void setUp() throws Exception {
		UserEntityDTO userEntityDTO = new UserEntityDTO();
		userEntityDTO
				.setAddress("CHRIS NISWANDEE SMALLSYS INC 795 E DRAGRAM TUCSON AZ 85705 USA");
		userEntityDTO.setBalance(500);
		userEntityDTO.setCity("Juneau");
		userEntityDTO.setCountry("USA");
		userEntityDTO.setEmail("aba@gmail.com");
		userEntityDTO.setGender("Male");
		userEntityDTO.setMobile("9874563210");
		userEntityDTO.setName("ALAN TURING");
		userEntityDTO.setPassword("asdas");
		userEntityDTO.setPlanId(1);
		userEntityDTO.setState("Alaska");
		userEntityDTO.setZipcode("232123");
		userService.registerUser(userEntityDTO);
		userService.requestBook("aba@gmail.com", 1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetUserHistory() throws ServiceLayerException {
		assertNotNull(userService.getUserHistory("aba@gmail.com"));
	}

	@Test
	public void testRegisterUser() throws ServiceLayerException {
		UserEntityDTO userEntityDTO = new UserEntityDTO();
		userEntityDTO
				.setAddress("CHRIS NISWANDEE SMALLSYS INC 795 E DRAGRAM TUCSON AZ 85705 USA");
		userEntityDTO.setBalance(500);
		userEntityDTO.setCity("Juneau");
		userEntityDTO.setCountry("USA");
		userEntityDTO.setEmail("ab@gmail.com");
		userEntityDTO.setGender("Male");
		userEntityDTO.setMobile("9874563210");
		userEntityDTO.setName("ALAN TURING");
		userEntityDTO.setPassword("asdas");
		userEntityDTO.setPlanId(1);
		userEntityDTO.setState("Alaska");
		userEntityDTO.setZipcode("232123");
		assertTrue(userService.registerUser(userEntityDTO));
	}

	@Test
	public void testUpdateUser() throws ServiceLayerException {
		UserEntityDTO userEntityDTO = new UserEntityDTO();
		userEntityDTO
				.setAddress("CHRIS NISWANDEE SMALLSYS INC 795 E DRAGRAM TUCSON AZ 85705 USA");
		userEntityDTO.setBalance(250);
		userEntityDTO.setCity("Juneau");
		userEntityDTO.setCountry("USA");
		userEntityDTO.setEmail("aba@gmail.com");
		userEntityDTO.setGender("Male");
		userEntityDTO.setMobile("9874563210");
		userEntityDTO.setName("ALAN TURING");
		userEntityDTO.setPassword("asdas");
		userEntityDTO.setPlanId(1);
		userEntityDTO.setState("Alaska");
		userEntityDTO.setZipcode("232123");
		assertTrue(userService.updateUser(userEntityDTO));
	}

	@Test
	public void testGetAllUserRequests() throws ServiceLayerException {
		assertNotNull(userService.getAllUserRequests());
	}

	@Test
	public void testGetAllBookRequests() throws ServiceLayerException {
		assertNotNull(userService.getAllBookRequests("aba@gmail.com"));
	}

	@Test
	public void testRequestBook() throws ServiceLayerException {
		assertTrue(userService.requestBook("aba@gmail.com", 11));
	}

	@Test
	public void testReturnBook() throws ServiceLayerException {
		assertTrue(userService.returnBook(3));
	}

	@Test
	public void testCancelRequest() throws ServiceLayerException {
		assertTrue(userService.cancelRequest(3, "abhishek@gmail.com"));
	}

	@Test
	public void testApproveUserRequest() throws ServiceLayerException {
		assertNotNull(userService.approveUserRequest("abhishek@gmail.com", 2));
	}

	@Test
	public void testGetAllUsersWithActiveSubscription()
			throws ServiceLayerException {
		assertNotNull(userService.getAllUsersWithActiveSubscription());
	}

	@Test
	public void testGetUserByEmail() throws ServiceLayerException {
		assertNotNull(userService.getUserByEmail("aba@gmail.com"));
	}

	@Test
	public void testCheckEmailForNull() throws ServiceLayerException {
		assertNotNull(userService.checkEmailForNull("aba@gmail.com"));
	}

	@Test
	public void testGetActiveSubscriptionDTO() throws ServiceLayerException {
		assertNotNull(userService.getActiveSubscriptionDTO("aba@gmail.com"));
	}

	@Test
	public void testSetUserNewAddress() throws ServiceLayerException {
		UserEntityDTO userEntityDTO = new UserEntityDTO();
		userEntityDTO.setAddress("New Jersey");
		assertTrue(userService
				.setUserNewAddress(userEntityDTO, "aba@gmail.com"));
	}

	@Test
	public void testChangePassword() throws ServiceLayerException {
		UserEntityDTO userEntityDTO = new UserEntityDTO();
		userEntityDTO.setAddress("New Jersey");
		assertFalse(userService.changePassword("aba@gmail.com", "assd", null));
		assertFalse(userService.changePassword("aba@gmail.com", "asd", "asdas"));
		assertFalse(userService.changePassword("aba@gmail.com", "ssada", "da"));
	}
}
