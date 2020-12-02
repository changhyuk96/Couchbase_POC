package com.poc.spring.service;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.StopWatch;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.StringDocument;

public class CouchbaseThread implements Runnable{
	  int docContentSize;
      int docCount;
      int docIdSize;
      Bucket bucket;
     
    public CouchbaseThread(int docCotentSize,int docCount, int docIdSize, Bucket bucket) {
    	this.docContentSize = docCotentSize;
    	this.docCount = docCount;
    	this.docIdSize = docIdSize;
    	this.bucket = bucket;
    }
//    public void run() {
//    	try {
//    		while (docCount > 0) {
//    			--docCount;
//    			String doc = "{\"a\":\"" + RandomStringUtils.randomAlphanumeric(docContentSize - 11) + "\"}";
//    			StringDocument ddoc = StringDocument.create(RandomStringUtils.randomAlphanumeric(docIdSize), doc);
//    			bucket.upsert(ddoc);
//    		}
//    	}catch(Exception e) {
//    		
//    	}
//    }
    public void run() {
        try {
        	while (docCount > 0) {
        		--docCount;
        		StringDocument ddoc = StringDocument.create(RandomStringUtils.randomAlphanumeric(docIdSize), "{\"a\":\"" + RandomStringUtils.randomAlphanumeric(docContentSize) + "\"}");
    			bucket.upsert(ddoc);
        	}
        }catch(Exception e) {

        }
    }
}
