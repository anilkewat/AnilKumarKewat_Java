package com.codingassignment.util;

import org.json.simple.JSONArray;

	public interface InputTrnsctnReader {
		
		 JSONArray readInputTrnsctnFile(String inputTrnsctnFilePath) throws Exception;

}
