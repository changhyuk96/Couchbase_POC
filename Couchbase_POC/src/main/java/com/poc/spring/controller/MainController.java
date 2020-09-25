package com.poc.spring.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.poc.spring.service.CouchbaseService;

@Controller
public class MainController {
	
	@Autowired
	CouchbaseService couchbaseService;
	
	@RequestMapping("/")
	public String home() { 
		return "settings"; 
	} 
	
	@RequestMapping("/settings")
	public String settings() {
		return "settings";
	}
	
	@RequestMapping("/nodeManagePage")
	public String nodeManagePage(Model model) {
		model.addAttribute("nodeList", couchbaseService.getNodeList());
		return "nodeManagePage";
	}
	
	@RequestMapping("/randomDataPage")
	public String randomDataPage() {
		return "randomDataPage";
	}
	@RequestMapping("/bucketManagePage")
	public String bucketManagePage(Model model) {
		model.addAttribute("bucketList", couchbaseService.getBucketList());
		return "bucketManagePage";
	}
	@RequestMapping("/dropBucketPage")
	public String dropBucketPage() {
		return "dropBucketPage";
	}
	@RequestMapping("/documentManagePage")
	public String documentManagePage() {
		return "documentManagePage";
	}
	@RequestMapping("/CsvOrFileUpsertPage")
	public String CsvOrFileUpsertPage() {
		return "CsvOrFileUpsertPage";
	}
	
	@RequestMapping("/header")
	public String header() { 
		return "header"; 
	} 
	
	@RequestMapping(value="/hey", method=RequestMethod.POST) 
	public String postMethod2(HttpServletRequest request) { 
		return "hey post"; 
	}

	

}
