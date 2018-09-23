package com.codingassignment.util;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONObject;

import com.codingassignment.bean.InstrumentBean;

public class CSVDataReaderWithHeader implements StartOfDayReader, Serializable{
	private String caret;
	public CSVDataReaderWithHeader(String caret){
		this.caret=caret;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<InstrumentBean> readStartOfDayFile(String startOfDayFileName,
			List<JSONObject> errorsInStartOfDayFile) throws IOException {
		List<InstrumentBean> list = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get(startOfDayFileName))) {
			List<String> startOfDayList = stream.collect(Collectors.toList());
			if (!startOfDayList.isEmpty()) {

				startOfDayList.remove(0);
				final String caret=this.caret;
				Predicate<String> isValidStartOfDayRecord = new Predicate<String>() {

					@SuppressWarnings("unchecked")
					@Override
					public boolean test(String line) {
						JSONObject error = new JSONObject();
						errorsInStartOfDayFile.add(error);
						
						if (line == null || line.trim().isEmpty()) {
							error.put("error", "startOfThedayLine is Null of Empty.");
							return false;
						}
						String lineParts[] = line.split(caret);
						if (lineParts.length < 4) {
							error.put("error", line + " Is not valid record.");
							return false;
						}
						if (isNumber(lineParts[1].trim()) == false) {
							error.put("error", line + ": Account is not number.");
							return false;
						}
						if (isNumber(lineParts[3].trim()) == false) {
							error.put("error", line + ": Quantity is not number.");
							return false;
						}

						return true;
					}

					private boolean isNumber(String value) {
						return value.matches("-?\\d+(\\.\\d+)?");
					}
				};

				list = startOfDayList.stream().filter(isValidStartOfDayRecord).map((line) -> {
					String[] lineParts = line.split(caret);
					InstrumentBean bean = new InstrumentBean();
					bean.setInstrument(lineParts[0]);
					bean.setAccount(Integer.parseInt(lineParts[1]));
					bean.setAccountType(lineParts[2]);
					bean.setQuantity(Double.parseDouble(lineParts[3]));
					return bean;
				}).collect(Collectors.toList());

			}
		}
		return list;

	}
	
}
