package com.codingassignment.processor;

import java.util.Comparator;

import org.json.simple.JSONObject;

@SuppressWarnings("rawtypes")
public class InputTrnsctnComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		JSONObject trnsctn1 = (JSONObject) o1;
		JSONObject trnsctn2 = (JSONObject) o2;
		return Integer.valueOf(trnsctn1.get("TransactionId").toString())
				.compareTo(Integer.valueOf(trnsctn2.get("TransactionId").toString()));
	}

}
