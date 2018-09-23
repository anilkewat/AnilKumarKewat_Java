package com.codingassignment.processor;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;

import com.codingassignment.bean.InstrumentBean;

public interface ICodingAssignmentProcessor {
	
	void process(List<InstrumentBean> startOfDayPositionList, JSONArray inputTrnsctnList,String outPutFolderPath) throws IOException;
}
