package com.weblearning.bookstore.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.weblearning.bookstore.persistance.entities.ActiveBookRequests;
import com.weblearning.bookstore.persistance.entities.BookReqHistory;
import com.weblearning.bookstore.persistance.entities.UserHistory;

public class Utilities {

	public static Date computeDate(Date date, long numDays) {
		long dateMillis = date.getTime();
		dateMillis += (numDays * PropertyConstants.DAYDURATION_MILLIS);
		return new Date(dateMillis);
	}

	public static Long computeNumberOfDays(long timemilliseconds) {
		return timemilliseconds / PropertyConstants.DAYDURATION_MILLIS;
	}

	public static String saveImage(MultipartFile file) {
		if (!(file == null || file.isEmpty())) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				File dir = new File(PropertyConstants.IMAGE_PATH);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + file.getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				return dir.getAbsolutePath() + File.separator
						+ file.getOriginalFilename();
			} catch (Exception e) {
				return "You failed to upload " + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + " because the file was empty.";
		}
	}

	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateformatddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
		String date_to_string = dateformatddMMyyyy.format(date);
		return date_to_string;
	}

	public static String saveFile(MultipartFile file) {
		if (!(file.getContentType().equals("text/xml") || file.getContentType()
				.equals("application/xml"))) {
			return PropertyConstants.WRONG_INPUT;
		}
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				String fileSavePath = System.getProperty("catalina.base");
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(fileSavePath
								+ File.separator + file.getOriginalFilename())));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
			}
		}
		return file.getOriginalFilename();
	}

	public static boolean checkForNumber(String id) {
		char[] chars = id.toCharArray();
		for (char c : chars) {
			if (c < 48 || c > 57) {
				return false;
			}
		}
		return true;
	}

	public static List<Long> getAllBookIdsFromHistory(
			Set<UserHistory> userHistories) {
		List<Long> retList = new ArrayList<Long>();
		if (userHistories == null) {
			return null;
		}
		for (UserHistory userHistory : userHistories) {
			Set<BookReqHistory> bookReqHistories = userHistory
					.getBookReqHisory();
			for (BookReqHistory bookReqHistory : bookReqHistories) {
				retList.add(bookReqHistory.getBookId());
			}
		}
		return retList;
	}

	public static List<Long> getAllBookIdsFromActiveRequests(
			Set<ActiveBookRequests> activeBookRequests) {
		List<Long> retList = new ArrayList<Long>();
		for (ActiveBookRequests activeBookRequest : activeBookRequests) {
			retList.add(activeBookRequest.getBookId());
		}
		return retList;
	}

	public static String getCurrentTimeStamp() {
		long timeStamp = new Date().getTime();
		return Long.toString(timeStamp);
	}

}