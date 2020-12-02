package com.poc.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poc.spring.service.CouchbaseService;

@Controller
public class PageController {
	
	@Autowired
	CouchbaseService couchbaseService;
	
	@RequestMapping("/")
	public String home() { 

		return "index"; 
	} 
	@RequestMapping("/testFile")
	public String testFile() { 
		
		return "testFile"; 
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
	@RequestMapping("/CsvOrFileUpsertPage")
	public String CsvOrFileUpsertPage() {
		return "CsvOrFileUpsertPage";
	}
	
	@RequestMapping("/header")
	public String header() { 
		return "header"; 
	} 
	
	@RequestMapping(value="/logPage") 
	public String logPage(Model model) { 
		
		model.addAttribute("logList", couchbaseService.getLogs());
		return "logPage"; 
	}
	
	@RequestMapping(value="/documentPage") 
	public String documentPage(Model model,HttpServletRequest request) { 
		
		model.addAttribute("documentList", couchbaseService.getDocumentList(request));
		model.addAttribute("bucketList", couchbaseService.getBucketList());
		
		String bucketName;
		if(request.getParameter("bucketName")==null || request.getParameter("bucketName") =="") {
			
			if(couchbaseService.bucket==null)
				return "documentPage";
			bucketName = couchbaseService.bucket.name();
		}
		else
			bucketName = request.getParameter("bucketName");
		
		model.addAttribute("bucketName",bucketName);
		
		return "documentPage"; 
	}

	
	
	@RequestMapping(value="/documentDetails") 
	public String documentDetails(Model model, HttpServletRequest request) { 
		
		model.addAttribute("documentId", request.getParameter("documentId"));
		model.addAttribute("documentDetails", couchbaseService.getDocumentDetails(request.getParameter("documentId"),request.getParameter("bucketName")));
		
		String bucketName;
		if(request.getParameter("bucketName")==null || request.getParameter("bucketName") =="")
			bucketName = couchbaseService.bucket.name();
		else
			bucketName = request.getParameter("bucketName");
		model.addAttribute("bucketName",bucketName);
		
		return "documentDetails"; 
	}
	
	@RequestMapping(value="/newDocument") 
	public String newDocument(Model model, HttpServletRequest request) { 
		
		model.addAttribute("bucketName", request.getParameter("bucketName"));
		return "newDocument"; 
	}
	
	@RequestMapping(value="/autoCompactionPage") 
	public String autoCompactionPage() { 
		return "autoCompactionPage"; 
	}
	
	@RequestMapping(value="/queryExcutePage") 
	public String queryExcutePage() { 
		return "queryExcutePage"; 
	}
	
	@RequestMapping(value="/emailAlertsPage") 
	public String emailAlertsPage() { 
		return "emailAlertsPage"; 
	}
	
	@RequestMapping(value="/sampleBucketPage") 
	public String sampleBucketPage() { 
		return "sampleBucketPage"; 
	}
	
	@RequestMapping(value="/ftsPage") 
	public String ftsPage(Model model) { 
		model.addAttribute("FTIList", couchbaseService.getFTIList());
		return "ftsPage"; 
	}
	
	@RequestMapping(value="/querySettingPage") 
	public String querySettingPage() { 
		return "querySettingPage"; 
	}
	
	@RequestMapping(value="/addAnalyzerPage") 
	public String addAnalyzerPage() { 
		return "addAnalyzerPage"; 
	}
	
	@RequestMapping(value="/eventingPage") 
	public String eventingPage() { 
		return "eventingPage"; 
	}
	
	@RequestMapping(value="/analyticsPage") 
	public String analyticsPage() { 
		return "analyticsPage"; 
	}
	
	@RequestMapping(value="/addEventingFunction") 
	public String addEventingFunction() { 
		return "addEventingFunction"; 
	}
	
	@RequestMapping(value="/searchResultPage") 
	public String searchResultPage(Model model,HttpServletRequest request) {
		
		model.addAttribute("bucketName",request.getParameter("bucketName"));
		model.addAttribute("documentList", couchbaseService.getFTSResult(request));
		return "searchResultPage"; 
	}
	
	@RequestMapping(value="/addFTIPage") 
	public String addFTIPage(Model model) {
		model.addAttribute("bucketList", couchbaseService.getBucketList());
		
		return "addFTIPage"; 
	}
	
	@RequestMapping(value="/hey", method=RequestMethod.POST) 
	public String postMethod2(HttpServletRequest request) { 
		
		return "hey post"; 
	}

}
