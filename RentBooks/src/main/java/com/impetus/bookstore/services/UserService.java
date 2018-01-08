package com.impetus.bookstore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.impetus.bookstore.dto.ActiveBookRequestDTO;
import com.impetus.bookstore.dto.ActiveSubscriptionDTO;
import com.impetus.bookstore.dto.UserEntityDTO;
import com.impetus.bookstore.exception.DAOLayerException;
import com.impetus.bookstore.exception.ServiceLayerException;
import com.impetus.bookstore.persistance.entities.UserEntity;
import com.impetus.bookstore.persistance.entities.UserHistory;

public interface UserService {

	public List<UserHistory> getUserHistory(String email)
			throws ServiceLayerException;

	public boolean registerUser(UserEntityDTO userEntityDTO)
			throws ServiceLayerException;

	public boolean updateUser(UserEntityDTO userEntityDTO)
			throws ServiceLayerException;

	public Map<String, ArrayList<ActiveBookRequestDTO>> getAllUserRequests()
			throws ServiceLayerException;

	public ArrayList<ActiveBookRequestDTO> getAllBookRequests(String email)
			throws ServiceLayerException;

	public boolean requestBook(String email, long bookId)
			throws ServiceLayerException;

	public boolean returnBook(long requestId) throws ServiceLayerException;

	public boolean cancelRequest(long requestId, String email)
			throws ServiceLayerException;

	public String approveUserRequest(String email, long requestId)
			throws ServiceLayerException;

	public List<UserEntity> getAllUsersWithActiveSubscription()
			throws ServiceLayerException;

	public UserEntityDTO getUserByEmail(String email)
			throws ServiceLayerException;

	public Boolean checkEmailForNull(String email) throws ServiceLayerException;

	public ActiveSubscriptionDTO getActiveSubscriptionDTO(String email)
			throws ServiceLayerException;

	public boolean setUserNewAddress(UserEntityDTO userEntityDTO, String email)
			throws ServiceLayerException;

	public boolean changePassword(String email, String password,
			String oldpassword) throws ServiceLayerException;;

	public void myServiceLayerException(String message,
			DAOLayerException daoLayerException) throws ServiceLayerException;

}
