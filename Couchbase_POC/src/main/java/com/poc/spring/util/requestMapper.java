package com.poc.spring.util;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class requestMapper {
	
	public HashMap<String, String> convertMap(HttpServletRequest request) {
		 
	    HashMap<String, String> hmap = new HashMap<String, String>();
	    String key;
	 
	    Enumeration<?> enum1 = request.getParameterNames();
	 
	    while (enum1.hasMoreElements()) {
	        key = (String) enum1.nextElement();
	        hmap.put(key, request.getParameter(key));
	    }
	 
	    return hmap;
	}

}
