package com.codingassignment.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InputTrnsctnJsonReader implements InputTrnsctnReader, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JSONArray readInputTrnsctnFile(String inputTrnsctnFilePath) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser=new JSONParser();
		JSONArray inputTrnsctnArray=(JSONArray) parser.parse(new BufferedReader(new FileReader(new File(inputTrnsctnFilePath))));
		return inputTrnsctnArray;
	}

}
