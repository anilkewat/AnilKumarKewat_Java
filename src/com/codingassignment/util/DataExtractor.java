/**
 * 
 */
package com.codingassignment.util;

import java.io.Serializable;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.codingassignment.bean.InstrumentBean;

/**
 * @author Anil
 *
 */
public class DataExtractor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static List<InstrumentBean> extractStartOfDayrecords(StartOfDayReader reader,
			List<JSONObject> errorsInStartOfDayFile, String startOfDayFilePath) throws Exception {

		return reader.readStartOfDayFile(startOfDayFilePath, errorsInStartOfDayFile);
	}

	public static JSONArray readInputTrnsctnFile(InputTrnsctnReader inputTrnsctnReader,String inputTrnsctnFilePath) throws Exception {
		return inputTrnsctnReader.readInputTrnsctnFile(inputTrnsctnFilePath);
	}

}
