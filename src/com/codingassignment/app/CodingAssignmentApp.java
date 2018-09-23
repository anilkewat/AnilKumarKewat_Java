package com.codingassignment.app;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.codingassignment.bean.InstrumentBean;
import com.codingassignment.processor.CodingAssignmentProcessor;
import com.codingassignment.util.CSVDataReaderWithHeader;
import com.codingassignment.util.DataExtractor;
import com.codingassignment.util.InputTrnsctnJsonReader;

public class CodingAssignmentApp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(CodingAssignmentApp.class.getName());

	public static void main(String[] args) {

		if (isArguamentsValid(args)) {
			final List<JSONObject> errorsInStartOfDayFile = new ArrayList<>();
			List<InstrumentBean> startOfDayPositionList = null;
			JSONArray inputTrnsctnList = null;
			try {

				startOfDayPositionList = DataExtractor.extractStartOfDayrecords(new CSVDataReaderWithHeader(","),
						errorsInStartOfDayFile, args[0]);

			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error in reading start of the day file." + e.getMessage());
				return;
			}

			try {
				inputTrnsctnList = DataExtractor.readInputTrnsctnFile(new InputTrnsctnJsonReader(), args[1]);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error in reading input transaction file." + e.getMessage());
				return;
			}

			CodingAssignmentProcessor processor = new CodingAssignmentProcessor();
			try {
				processor.process(startOfDayPositionList, inputTrnsctnList,args[0].substring(0,args[0].lastIndexOf(System.getProperty("file.separator") )));
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Could not generate end of the day output file due to :" + e.getMessage());

			}
			for(JSONObject error:errorsInStartOfDayFile){
				if(error.isEmpty()==false){
					logger.log(Level.INFO,error.toJSONString());
				}
				
			}

		}

	}

	private static boolean isArguamentsValid(String[] args) {
		if (args.length == 0) {
			logger.log(Level.SEVERE,
					"Please provide valid arguments to run program. Valid command to run is: java CodingAssignmentApp <Input_StartOfDay_Positions file path> <Input_Transactions> file path");
			return false;
		}
		if (args.length == 1) {
			logger.log(Level.SEVERE,
					"Please provide valid arguments to run program. Valid command to run is: java CodingAssignmentApp <Input_StartOfDay_Positions file path> <Input_Transactions> file path");
			return false;
		}

		if (args.length > 2) {
			logger.log(Level.SEVERE,
					"Please provide valid arguments to run program. Valid command to run is: java CodingAssignmentApp <Input_StartOfDay_Positions file path> <Input_Transactions> file path");
			return false;
		}

		if (args[0] == null || args[1] == null) {
			logger.log(Level.SEVERE,
					"Please provide valid arguments to run program. Valid command to run is: java CodingAssignmentApp <Input_StartOfDay_Positions file path> <Input_Transactions> file path");
			return false;
		}

		if (!args[0].contains(".txt")) {
			logger.log(Level.SEVERE, "Please provide valid file for Input_StartOfDay_Positions. Valid is text a file.");
			return false;
		}

		if (!args[1].contains(".txt")) {
			logger.log(Level.SEVERE, "Please provide valid file for Input_Transactions. Valid is text a file.");
			return false;
		}

		return true;
	}

}
