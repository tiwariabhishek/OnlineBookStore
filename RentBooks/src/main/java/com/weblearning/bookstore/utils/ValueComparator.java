package com.weblearning.bookstore.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<String> {

	Map<String, ArrayList<Long>> base;

	public ValueComparator(Map<String, ArrayList<Long>> base) {
		this.base = base;
	}

	public int compare(String a, String b) {
		if (base.get(a).size() < base.get(b).size()) {
			return -1;
		} else {
			return 1;
		}
	}
}
