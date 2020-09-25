package com.poc.spring.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.poc.spring.service.CouchbaseService;

@Controller
public class ServiceController {
	
	@Autowired
	CouchbaseService couchbaseService;
	
	
	@RequestMapping(value="/n1qlExcute", method=RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> n1qlExcute(HttpServletRequest request) throws Exception { 
		return couchbaseService.excuteDataN1QL(request); 
	}
	
	@RequestMapping(value="/sdkJobExcute", method=RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> sdkJobExcute(HttpServletRequest request) throws Exception { 
		return couchbaseService.excuteSdkJob(request); 
	}
	
	@RequestMapping(value="/fileUpload", method=RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> fileUpload(MultipartHttpServletRequest mRequest) throws Exception { 
		return couchbaseService.uploadFile(mRequest); 
	}
	
	@RequestMapping(value="/randomData", method=RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> randomData(HttpServletRequest request) throws Exception { 
		return couchbaseService.makeRandomData(request); 
	}

	@RequestMapping(value="/conData", method=RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> conData(HttpServletRequest request) throws Exception { 
		return couchbaseService.connectionData(request); 
	}
	
	@RequestMapping(value="/dropBucket", method=RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> dropBucket(HttpServletRequest request) throws Exception { 
		return couchbaseService.dropBucket(request);
	}
	
	@RequestMapping(value="/createBucket", method=RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> createBucket(HttpServletRequest request) throws Exception { 
		return couchbaseService.createBucket(request); 
	}
	
	@RequestMapping(value="/dropNode", method=RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> dropNode(HttpServletRequest request) throws Exception { 
		return couchbaseService.dropNode(request); 
	}

	@RequestMapping(value="/addNode", method=RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> addNode(HttpServletRequest request) throws Exception { 
		return couchbaseService.addNode(request); 
	}
	
	
	@RequestMapping(value="/rebalancing", method=RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> rebalancing(HttpServletRequest request) throws Exception { 
		return couchbaseService.rebalancing(request); 
	}
	
}
