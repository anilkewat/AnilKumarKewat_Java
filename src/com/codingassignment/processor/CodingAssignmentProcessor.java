package com.codingassignment.processor;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.codingassignment.bean.InstrumentBean;

public class CodingAssignmentProcessor implements Serializable, ICodingAssignmentProcessor{

	private static final Logger logger = Logger.getLogger(CodingAssignmentProcessor.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public void process(List<InstrumentBean> startOfDayPositionList, JSONArray inputTrnsctnList,String outPutFolderPath) throws IOException{
		Collections.sort(inputTrnsctnList,new InputTrnsctnComparator());
		Map<String,List<InstrumentBean>> instrumentMap=startOfDayPositionList.stream().collect(Collectors.groupingBy(InstrumentBean::getInstrument));
		
		for(Entry<String, List<InstrumentBean>> entry:instrumentMap.entrySet()){
			
			List<JSONObject> list = (List<JSONObject>) inputTrnsctnList
					.stream()
					.filter(obj -> ((JSONObject)obj).get("Instrument").toString().equalsIgnoreCase(entry.getKey()))
					.map(obj ->{ return ((JSONObject)obj);}).collect(Collectors.toList());
			if(list.isEmpty()){
				logger.info("No data found for Instrument "+entry.getKey()+" in transaction file" );
			}else{
				calculateEndOfDayPosition(list,entry.getKey(),entry.getValue());
			}
		}
		List<String> outputLines=new ArrayList<>();
		outputLines.add("Instrument,Account,AccountType,Quantity,Delta");
		for(InstrumentBean outPutBean:startOfDayPositionList){
			outputLines.add(outPutBean.toString());
		}
		Files.write(Paths.get(outPutFolderPath+"/Expected_EndOfDay_Positions_"+LocalDateTime.now().getNano()+".txt"), outputLines, StandardOpenOption.CREATE_NEW);
				
		logger.info(outputLines.toString());
		
	}

	private void calculateEndOfDayPosition(List<JSONObject> trnsctnList, String key, List<InstrumentBean> instrumentList) {
		double originalIntstrumentEValue=0;
		Optional<InstrumentBean> eInstrument=instrumentList.stream().filter(instrument->instrument.getAccountType().equalsIgnoreCase("E")).findAny();
		if(eInstrument.isPresent()){
			originalIntstrumentEValue=eInstrument.get().getQuantity();
		}
		double originalIntstrumentIValue=0;
		Optional<InstrumentBean> iInstrument=instrumentList.stream().filter(instrument->instrument.getAccountType().equalsIgnoreCase("I")).findAny();
		if(iInstrument.isPresent()){
			originalIntstrumentIValue=iInstrument.get().getQuantity();
		}
		
		for(JSONObject trnsctn:trnsctnList){
			if(trnsctn.get("TransactionType").toString().equalsIgnoreCase("B")){
				for(InstrumentBean instrument:instrumentList){
					if(instrument.getAccountType().equalsIgnoreCase("E")){
						instrument.setQuantity(instrument.getQuantity()+Double.parseDouble(trnsctn.get("TransactionQuantity").toString()));
						instrument.setDelta(instrument.getQuantity()-originalIntstrumentEValue);
					}
					if(instrument.getAccountType().equalsIgnoreCase("I")){
						instrument.setQuantity(instrument.getQuantity()-Double.parseDouble(trnsctn.get("TransactionQuantity").toString()));
						instrument.setDelta(instrument.getQuantity()-originalIntstrumentIValue);
					}
				}
			}
			if(trnsctn.get("TransactionType").toString().equalsIgnoreCase("S")){
				for(InstrumentBean instrument:instrumentList){
					if(instrument.getAccountType().equalsIgnoreCase("E")){
						instrument.setQuantity(instrument.getQuantity()-Double.parseDouble(trnsctn.get("TransactionQuantity").toString()));
						instrument.setDelta(instrument.getQuantity()-originalIntstrumentEValue);
					}
					if(instrument.getAccountType().equalsIgnoreCase("I")){
						instrument.setQuantity(instrument.getQuantity()+Double.parseDouble(trnsctn.get("TransactionQuantity").toString()));
						instrument.setDelta(instrument.getQuantity()-originalIntstrumentIValue);

					}
				}
			}
		}
		
	}

}
