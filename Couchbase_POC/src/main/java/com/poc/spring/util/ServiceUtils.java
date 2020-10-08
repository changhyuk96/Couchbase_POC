package com.poc.spring.util;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class ServiceUtils {
	
	public Map<String,Object> curlExcute(String command) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
		try {
			Process process = processBuilder.start();
			String result = IOUtils.toString(process.getInputStream(),StandardCharsets.UTF_8.name());
			
			if(result.trim().isEmpty() || result == null || result.equals("[]")) {
				resultMap.put("result", "실행이 정상적으로 완료되었습니다.");
				process.destroy();
				return resultMap;
			}
			
			resultMap.put("result", result);
			process.destroy();
			
		}catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "서버쪽 에러입니다.");
			
			return resultMap;
		}
		
		return resultMap;
	}
	
	public String byteToMb(Object object) {
		DecimalFormat df = new DecimalFormat("##0.0");
		
		String result;
		
		if(object == null) {
			result = "0.0";
			return result;
		}
		
		result = df.format(((long)object)*10/1024/1024*0.1);
		
		return result;
	}
	
	public String doubleFormat(Object object) {
		DecimalFormat df = new DecimalFormat("##0.0");
		
		String result;
		
		if(object == null) {
			result = "0.0";
			return result;
		}
		
		result =  df.format(object);
		
		return result;
	}
	
	public List<Object> serviceCheck(JSONArray serviceJsonList){
		
		List<Object> serviceList = new ArrayList<Object>();
		
		for (int j = 0; j < serviceJsonList.size(); j++) {
			switch((String) serviceJsonList.get(j)) {
				case "fts":
					serviceList.add("Search"); break;
				case "kv":
					serviceList.add("Data"); break;
				case "n1ql":
					serviceList.add("Query"); break;
				case "cbas":
					serviceList.add("Analytics"); break;
				case "eventing":
					serviceList.add("Eventing"); break;
				case "index":
					serviceList.add("Index"); break;
			}
		}
		
		return serviceList;
	}
	
	public List<Object> logMaker(StringBuilder command, String ...name){
		
		String names[] = name;
		List<Object> logList = new ArrayList<Object>();
		Map<Object,Object> logMap = new HashMap<Object,Object>();
		
		for(String logName : names) {
			String cmd = command.substring(0, command.lastIndexOf("/"))+"/"+logName;
			System.out.println(cmd);
			Map<String, Object> resultMap = curlExcute(cmd);
			
			Object obj = resultMap.get("result");
			
			logMap.put(logName,obj);
		}
		
		logList.add(logMap);
		return logList;
	}
}
