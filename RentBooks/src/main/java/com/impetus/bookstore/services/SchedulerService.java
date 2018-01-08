package com.impetus.bookstore.services;

import com.impetus.bookstore.exception.ServiceLayerException;

public interface SchedulerService {

	public void csvFileUpload() throws ServiceLayerException;

	public void recommendBooks() throws ServiceLayerException;

	public void addDetailsToHistory() throws ServiceLayerException;
}
