package com.codingassignment.util;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;

import com.codingassignment.bean.InstrumentBean;

public interface StartOfDayReader {
	
	List<InstrumentBean> readStartOfDayFile(String startOfDayFileName,
			List<JSONObject> errorsInStartOfDayFile) throws IOException;
}
