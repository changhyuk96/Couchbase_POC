package com.poc.spring.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.spring.dto.CompactionDTO;
import com.poc.spring.dto.ConnectDTO;
import com.poc.spring.dto.SettingDTO;
import com.poc.spring.util.ServiceUtils;


@Service
public class CouchbaseService {
	
	@Autowired
	ServiceUtils serviceUtil;
	
	Cluster cluster;
	Bucket bucket;
	ConnectDTO dto;
	CouchbaseEnvironment env;
	
	List<String> hostList = new ArrayList<String>();
	JSONParser parser = new JSONParser();
	ObjectMapper mapper = new ObjectMapper();
	
	public Map<String, Object> connectionData(HttpServletRequest request) throws Exception {

		request.setCharacterEncoding("utf-8");
		
		/** Connection Info **/
		String strHostName = request.getParameter("txtHostName");
		String portNumber = request.getParameter("portNumber");
		String strUserName = request.getParameter("txtUserName");
		String strPassword = request.getParameter("pwdPassword");
		String strBucketName = request.getParameter("txtBucketName");

		/** Timeout Info **/
		Long lKeyValueTO = Long.parseLong(request.getParameter("txtKeyValueTO"));
		Long lViewTO = Long.parseLong(request.getParameter("txtViewTO"));
		Long lQueryTO = Long.parseLong(request.getParameter("txtQueryTO"));
		Long lConnectTO = Long.parseLong(request.getParameter("txtConnectTO"));
		Long lDisConnectTO = Long.parseLong(request.getParameter("txtDisConnectTO"));
		Long lManagementTO = Long.parseLong(request.getParameter("txtManagementTO"));

		/** Bootstrap Info **/
		boolean isSslEnable = Boolean.parseBoolean(request.getParameter("rdoSslEnable"));
		String strSslKeyLoc = request.getParameter("txtSslKeyLoc");
		String strSslKeyPwd = request.getParameter("pwdSslKeyPwd");
		boolean isHttpEnable = Boolean.parseBoolean(request.getParameter("rdoHttpEnabled"));
		int intHttpDrtPort = Integer.parseInt(request.getParameter("txtHttpDirectPort"));
		int intHttpSslPort = Integer.parseInt(request.getParameter("txtHttpSslPort"));
		boolean isCarrEnable = Boolean.parseBoolean(request.getParameter("rdoCarrierEnable"));
		int intCarrDrtPort = Integer.parseInt(request.getParameter("txtCarrierDirectPort"));
		int intCarrSslPort = Integer.parseInt(request.getParameter("txtCarrierSslPort"));
		boolean isDnsSrvEnable = Boolean.parseBoolean(request.getParameter("rdoDnsSrvEnable"));
		boolean isMutatTknEnable = Boolean.parseBoolean(request.getParameter("rdoMutationTknEnable"));

		/** Reliability Info **/
		Long lMaxReqLifeTime = Long.parseLong(request.getParameter("txtMaxReqLifeTime"));
		Long lKeepAliveInterval = Long.parseLong(request.getParameter("txtKeepAliveInterval"));

		/** Performance Info **/
		int intKvEndpoints = Integer.parseInt(request.getParameter("txtKvEndpoints"));
		int intViewEndpoint = Integer.parseInt(request.getParameter("txtViewEndpoint"));
		int intQueryEndpoint = Integer.parseInt(request.getParameter("txtQueryEndpoint"));
		boolean isTcpNodelayEnable = Boolean.parseBoolean(request.getParameter("rdoTcpNodelayEnable"));

		/** Advanced Info **/
		int intRequestBufferSize = Integer.parseInt(request.getParameter("txtRequestBufferSize"));
		int intResponseBufferSize = Integer.parseInt(request.getParameter("txtResponseBufferSize"));
		boolean isBufferPoolEnab = Boolean.parseBoolean(request.getParameter("rdoBufferPoolEnab"));

		dto = new ConnectDTO(strHostName, portNumber, strUserName, strPassword, strBucketName, lKeyValueTO, lViewTO,
				lQueryTO, lConnectTO, lDisConnectTO, lManagementTO, isSslEnable, strSslKeyLoc, strSslKeyPwd,
				isHttpEnable, intHttpDrtPort, intHttpSslPort, isCarrEnable, intCarrDrtPort, intCarrSslPort,
				isDnsSrvEnable, isMutatTknEnable, lMaxReqLifeTime, lKeepAliveInterval, intKvEndpoints, intViewEndpoint,
				intQueryEndpoint, isTcpNodelayEnable, intRequestBufferSize, intResponseBufferSize, isBufferPoolEnab);

		env = DefaultCouchbaseEnvironment.builder()
					
				/** Timeout Info **/
				.kvTimeout(lKeyValueTO).viewTimeout(lViewTO).queryTimeout(lQueryTO).connectTimeout(lConnectTO)
				.disconnectTimeout(lDisConnectTO).managementTimeout(lManagementTO)

				/** Bootstrap Info **/
				.sslEnabled(isSslEnable).sslKeystoreFile(strSslKeyLoc).sslKeystorePassword(strSslKeyPwd)
				.bootstrapHttpEnabled(isHttpEnable).bootstrapHttpDirectPort(intHttpDrtPort)
				.bootstrapHttpSslPort(intHttpSslPort).bootstrapCarrierEnabled(isCarrEnable)
				.bootstrapCarrierDirectPort(intCarrDrtPort).bootstrapCarrierSslPort(intCarrSslPort)
				.dnsSrvEnabled(isDnsSrvEnable).mutationTokensEnabled(isMutatTknEnable)

				/** Advanced Info **/
			  .requestBufferSize(intRequestBufferSize)
			  .responseBufferSize(intResponseBufferSize)		
			  .bufferPoolingEnabled(isBufferPoolEnab)		 
			  
			  .build();
				  
		
		cluster = CouchbaseCluster.create(env,dto.getStrHostName());
		cluster.authenticate(dto.getStrUserName(),dto.getStrPassword());
		bucket = cluster.openBucket(dto.getStrBucketName());
		
		return null;
	}
	
	public Map<String, Object> addNode(HttpServletRequest request){
		// curl -v -X POST -u Admin:tf4220 http://localhost:8091/controller/addNode -d hostname=http://192.168.0.17
		// -d user=Administrator -d password=dpsxndpa1! -d services=kv, n1ql, index
		
		if(dto == null)
			return null;
		
		String[] checkedService = request.getParameterValues("service");
		
		StringBuilder command = new StringBuilder();
		command.append("curl -v -X POST -u ");
		command.append(dto.getStrUserName());
		command.append(":");
		command.append(dto.getStrPassword());
		command.append(" http://");
		command.append(dto.getStrHostName());
		command.append(":");
		command.append(dto.getPortNumber());
		command.append("/controller/addNode");
		command.append(" -d hostname=");
		command.append(request.getParameter("hostName"));
		command.append(" -d user=");
		command.append(dto.getStrUserName());
		command.append(" -d password=");
		command.append(dto.getStrPassword());
		command.append(" -d services=");
		for(int i=0;i<checkedService.length;i++) {
			command.append(checkedService[i]);
			if(checkedService.length-1 != i) {
				command.append(", ");
			}
		}
		System.out.println(command);
		
		Map<String, Object> resultMap = serviceUtil.curlExcute(command.toString());
		
		return resultMap;
		
	}
	
	public Map<String, Object> dropNode(HttpServletRequest request){
		// curl -u Admin:tf4220 http://localhost:8091/controller/ejectNode -d otpNode=ns_1@192.168.0.17 
		// 장애발생or보류or리밸런싱 안한 노드에만 사용가능함.
		
		// curl -u Admin:tf4220 http://localhost:8091/controller/rebalance -d ejectedNodes=ns_1%40192.168.0.17
		// 						-d knownNodes=ns_1%40192.168.0.27%2Cns_1%40192.168.0.17
		// rebalance 과정을 거치면서 안전하게 제거함.
		
		if(dto == null)
			return null;
		
		StringBuilder command = new StringBuilder();
		command.append("curl -u ");
		command.append(dto.getStrUserName());
		command.append(":");
		command.append(dto.getStrPassword());
		command.append(" http://");
		command.append(dto.getStrHostName());
		command.append(":");
		command.append(dto.getPortNumber());
		
		if(request.getParameter("activeCheck").equals("active")) {
			
			command.append("/controller/rebalance");
			command.append(" -d knownNodes=ns_1%40");
			command.append(hostList.get(0));
			
			for(int i=1;i<hostList.size();i++) {
				command.append("%2Cns_1%40");
				command.append(hostList.get(i));
			}
			
			command.append(" -d ejectedNodes=ns_1%40");
			command.append(request.getParameter("dropHostName"));
			
			
		}else {
			// 장애발생or보류or리밸런싱 안한 노드 삭제
			
			command.append("/controller/ejectNode");
			command.append(" -d otpNode=ns_1@");
			command.append(request.getParameter("dropHostName"));
		}
		System.out.println(command);
		Map<String, Object> resultMap = serviceUtil.curlExcute(command.toString());
		return resultMap;
	}
	
	public Map<String, Object> rebalancing(HttpServletRequest request){
		// curl -v -X POST -u [admin]:[password] http://[localhost]:8091/controller/rebalance
		// -d 'knownNodes=ns_1%4010.143.190.101%2Cns_1%4010.143.190.102%2Cns_1%4010.143.190.103'
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		if(dto == null) {
			resultMap.put("result", "환경 설정 및 서버 연결을 해주십시오.");
			return resultMap;
		} else if(hostList.size() <= 1) {
			resultMap.put("result", "서버가 한 개 뿐입니다.");
			return resultMap;
		}
		
		StringBuilder command = new StringBuilder();
		command.append("curl -v -X POST -u ");
		command.append(dto.getStrUserName());
		command.append(":");
		command.append(dto.getStrPassword());
		command.append(" http://");
		command.append(dto.getStrHostName());
		command.append(":");
		command.append(dto.getPortNumber());
		command.append("/controller/rebalance -d knownNodes=ns_1%40");
		command.append(hostList.get(0));
		
		for(int i=1;i<hostList.size();i++) {
			command.append("%2Cns_1%40");
			command.append(hostList.get(i));
		}
		
		System.out.println(command);
		
		resultMap = serviceUtil.curlExcute(command.toString());
		
		
		return resultMap;
	}
	
	public Map<String, Object> createBucket(HttpServletRequest request) {
		
		// curl -v -X POST http://localhost:8091/pools/default/buckets -u Administrator:password \
		// -d name=testBucket -d ramQuotaMB=256 -d bucketType=ephemeral
		
		StringBuilder command = new StringBuilder();
		command.append("curl -v X POST -u ");
		command.append(request.getParameter("userName"));
		command.append(":");
		command.append(request.getParameter("userPassword"));
		command.append(" http://");
		command.append(request.getParameter("hostName"));
		command.append(":");
		command.append(request.getParameter("portNumber"));
		command.append("/pools/default/buckets/");
		command.append(" -d name=");
		command.append(request.getParameter("newBucketName"));
		command.append(" -d ramQuotaMB=");
		command.append(request.getParameter("bucketMemory"));
		command.append(" -d bucketType=");
		command.append(request.getParameter("newBucketType"));
		
		if(!request.getParameter("newBucketType").equals("memcached")) {
			command.append(" -d replicaNumber=");
			command.append(request.getParameter("newBucketReplicas"));
			command.append(" -d flushEnabled=");
			command.append(request.getParameter("flushEnable"));
			if(request.getParameter("newBucketType").equals("couchbase")) {
				command.append(" -d replicaIndex=");
				command.append(request.getParameter("indexReplicaEnable"));
			}
		}
		
		System.out.println(command);
		
		Map<String, Object> resultMap = serviceUtil.curlExcute(command.toString());
		
		return resultMap;
	}
	
	public Map<String, Object> dropBucket(HttpServletRequest request) {
		
		StringBuilder command = new StringBuilder();
		command.append("curl -X DELETE -u ");
		command.append(request.getParameter("userName"));
		command.append(":");
		command.append(request.getParameter("userPassword"));
		command.append(" http://");
		command.append(request.getParameter("hostName"));
		command.append(":");
		command.append(request.getParameter("portNumber"));
		command.append("/pools/default/buckets/");
		command.append(request.getParameter("bucketName"));
		
		Map<String, Object> resultMap = serviceUtil.curlExcute(command.toString());
		
		return resultMap;
	}
	
	public Map<String, Object> makeRandomData(HttpServletRequest request) throws Exception {

		int docSize = Integer.parseInt(request.getParameter("docSize"));
		int docIdSize = Integer.parseInt(request.getParameter("docIdSize"));
		int docCount = Integer.parseInt(request.getParameter("docCount"));
		String docType = request.getParameter("docType");
		String loopYN = request.getParameter("loop");
		int threadCount = Integer.parseInt(request.getParameter("threadCount"));
		String docContent;
		
		if(bucket == null) {
			return null;
		}
		
		Runnable couchTr = new CouchbaseThread(docSize, docCount, docIdSize, bucket);

		for (int i = 0; i < threadCount; i++) {

			Thread t1 = new Thread(couchTr);
			t1.start();
		}
		
		return null;
	}
	
	public Map<String, Object> uploadFile(MultipartHttpServletRequest mRequest) throws Exception {

		String strLocalPath = "C:/upload/"; 			// 로컬 업로드 경로
		File file = new File(strLocalPath);				// 로컬 경로 파일
		String strFilePath = "";						// 파일 경로 + 파일명
		String docID = mRequest.getParameter("docId");	// docID
		
		 StringBuilder statement = new StringBuilder();
		 
		 // select count(*) from `test`as t where meta(t).id like "test__%";
		 
		 statement.append("select count(*) from `");
		 statement.append(bucket.name());
		 statement.append("` as t where meta(t).id like \"");
		 statement.append(docID+"__%\"");
		 
		 N1qlQueryResult result4 = bucket.query(N1qlQuery.simple(statement.toString()));
		 
		 N1qlQueryRow row = result4.allRows().get(0);
		 JsonObject content = row.value();
		 int num = Integer.parseInt(content.get("$1").toString());
		 
		 docID = docID+"_"+(num+1);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (!file.isDirectory()) { 			// 파일 디렉토리 확인 및 디렉토리 생성
			file.mkdir();
		}
		
		MultipartFile multipartFile = mRequest.getFile("fileName"); 	// fileName Request
		String strOriginFileName = multipartFile.getOriginalFilename(); // Original FileName
		strFilePath = strLocalPath + strOriginFileName; 				// File Path

		int intSubstrDot = strOriginFileName.lastIndexOf("."); 					// 파일 확장자
		String strExtention = strOriginFileName.substring(intSubstrDot + 1); 	// 파일 확장자
		
		String strThreadCount = mRequest.getParameter("threadCount");
		int threadCnt = 0;
		if(!"".equals(strThreadCount)) {
			threadCnt = Integer.parseInt(strThreadCount);	 // thread Count
		}

		if(!multipartFile.isEmpty()) {			//파일이 선택됐을 경우
			multipartFile.transferTo(new File(strFilePath));		//FilePath에 파일 생성
			resultMap.put("suc","OK");

			if(StringUtils.isNotBlank(docID) && threadCnt>0) {			//문서 아이디가 공백이 아니며, 쓰레드 개수가 0 이상일 때
				if (strExtention.equals("csv")) {					//파일 확장자가 csv일 경우
					
					CSVtoJSON csvToJson = new CSVtoJSON(bucket, multipartFile, strFilePath, docID, threadCnt, strExtention);
					csvToJson.CSVtoJSON();
					resultMap.put("mapFlag", "3");
					resultMap.put("csvInsert", "csv 파일 \"" + strOriginFileName + "\"가 insert 되었습니다.");
			
				} else if (strExtention.equals("json")) {			//파일 확장자가 json일 경우
					
//					Object obj =  parser.parse(new FileReader(strFilePath));
//			        JSONObject jsonObject =  (JSONObject) obj;
//			        String jsonStr = jsonObject.toString();
//					JsonObject content = JsonObject.fromJson(jsonStr);
//					JsonDocument doc = JsonDocument.create(docID, content); 
//					bucket.insert(doc);
					CSVtoJSON csvToJson = new CSVtoJSON(bucket, multipartFile, strFilePath, docID, threadCnt, strExtention);
					csvToJson.jsonUpload();
					resultMap.put("mapFlag","4");
					resultMap.put("jsonInsert","json 파일 \""+strOriginFileName+"\"이 insert 되었습니다.");
					
				} else {										//파일 확장자가 csv, json이 아닌 다른 것일 경우
					System.out.println(strOriginFileName);
					resultMap.put("mapFlag","2");
					resultMap.put("ExtentionsCheck","확장자가 csv 및 json인 파일을 선택해주세요.");
				}
			}
			else {
				resultMap.put("mapFlag","1");
				resultMap.put("idThreadCheck","문서 아이디와 쓰레드 개수에 빈칸 없이 입력해주세요.");
				System.out.println("docID or threadCnt is Null");
			}
		}else {
			resultMap.put("fileCheck","파일을 선택해주세요");
			System.out.println("File is not Selected");
		}

		return resultMap;
	}

	public List<Object> getNodeList(){
		// curl -u Administrator:password http://10.5.2.54:8091/pools/default/buckets
		
		if(dto == null)
			return null;
		
		StringBuilder command = new StringBuilder();
		command.append("curl -u ");
		command.append(dto.getStrUserName());
		command.append(":");
		command.append(dto.getStrPassword());
		command.append(" http://");
		command.append(dto.getStrHostName());
		command.append(":");
		command.append(dto.getPortNumber());
		command.append("/pools/default");
		System.out.println(command);
		
		List<Object> list = new ArrayList<Object>();
		Map<String,Object> map = serviceUtil.curlExcute(command.toString());
		
		hostList.clear();
		Object obj = null;
		try {
			obj = parser.parse(map.get("result").toString());
			JSONObject json = (JSONObject) obj;
			JSONArray array = (JSONArray) json.get("nodes");
			
			for(int i=0;i<array.size();i++) {
				JSONObject node = (JSONObject) array.get(i);
				JSONArray serviceJsonList = (JSONArray)node.get("services");
				List<Object> serviceList = serviceUtil.serviceCheck(serviceJsonList);
				JSONObject systemStats = (JSONObject)node.get("systemStats");
				
				Map<String,Object> nodeMap = new HashMap<String,Object>();
				
				String hostName = (String) node.get("hostname");
				hostList.add(hostName.substring(0,hostName.indexOf(":")));
				
				nodeMap.put("hostname", node.get("hostname"));
				nodeMap.put("service", serviceList);
				nodeMap.put("cpu", serviceUtil.doubleFormat(systemStats.get("cpu_utilization_rate")));
				nodeMap.put("swap", serviceUtil.byteToMb(systemStats.get("swap_total")));
				nodeMap.put("ram_total", serviceUtil.byteToMb(systemStats.get("mem_total")));
				nodeMap.put("ram_free", serviceUtil.byteToMb(systemStats.get("mem_free")));
				
				list.add(nodeMap);
			}
			
			return list;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Object> getBucketList(){
		// curl -u Administrator:password http://10.5.2.54:8091/pools/default/buckets
		
		if(dto == null)
			return null;
		
		StringBuilder command = new StringBuilder();
		command.append("curl -u ");
		command.append(dto.getStrUserName());
		command.append(":");
		command.append(dto.getStrPassword());
		command.append(" http://");
		command.append(dto.getStrHostName());
		command.append(":");
		command.append(dto.getPortNumber());
		command.append("/pools/default/buckets");
		
		Map<String,Object> map = serviceUtil.curlExcute(command.toString());
		List<Object> list = new ArrayList<Object>();
		
		Object obj = null;
		try {
			obj = parser.parse(map.get("result").toString());
			
			JSONArray array = (JSONArray) obj;
			
			for(int i=0;i<array.size();i++) {
				JSONObject jsonObj = (JSONObject)array.get(i);
				Object bufferObj = jsonObj.get("basicStats");
				JSONObject statObj = (JSONObject)bufferObj;
				bufferObj = jsonObj.get("quota");
				JSONObject quotaObj = (JSONObject)bufferObj;
				
				Map<String,Object> bucketMap = new HashMap<String,Object>();
				
				bucketMap.put("name", jsonObj.get("name"));
				bucketMap.put("bucketType", jsonObj.get("bucketType").equals("membase") ? "couchbase" : jsonObj.get("bucketType"));
				bucketMap.put("itemCount", statObj.get("itemCount"));
				bucketMap.put("memUsed", serviceUtil.byteToMb(statObj.get("memUsed")));
				bucketMap.put("diskUsed", serviceUtil.byteToMb(statObj.get("diskUsed")));
				bucketMap.put("quotaPercentUsed", serviceUtil.doubleFormat(statObj.get("quotaPercentUsed")));
				bucketMap.put("ram", serviceUtil.byteToMb(quotaObj.get("ram")));
				
				list.add(bucketMap);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Object> getLogs() {
		
		// curl -v -X GET -u Admin:tf4220 http://localhost:8091/sasl_logs/[logs-name]
		
		if(dto == null)
			return null;
		
		StringBuilder command = new StringBuilder();
		command.append("curl -X GET -u ");
		command.append(dto.getStrUserName());
		command.append(":");
		command.append(dto.getStrPassword());
		command.append(" http://");
		command.append(dto.getStrHostName());
		command.append(":");
		command.append(dto.getPortNumber());
		command.append("/sasl_logs/");
		
		String[] logs = { "views" ,
						"query"
						
						};
		
		List<Object> logList = serviceUtil.logMaker(command, logs);
		
		return logList;
	}
	
	public List<Object> getDocumentList(){
		
		if(dto == null)
			return null;
		 // select meta(t).id from `test` as t limit 30;
		
		 StringBuilder statement = new StringBuilder();
		 
		 statement.append("select *, meta(t).id from `");
		 statement.append(bucket.name());
		 statement.append("` as t limit 30");
		 
		 List<Object> list = new ArrayList<Object>();
		 
		 N1qlQueryResult result = bucket.query(N1qlQuery.simple(statement.toString()));
		 
		 for(N1qlQueryRow row : result.allRows()) {
			 JsonObject document = row.value();

			 Map<Object, Object> resultMap = new HashMap<Object, Object>();
			 resultMap.put("id", document.getString("id"));
			 resultMap.put("content",  document.getObject("t") );
			 
			 list.add(resultMap);
		 }
		 
		 return list;
	}
	
	public Object getDocumentDetails(String documentId) {

		StringBuilder statement = new StringBuilder();

		// select * from `test` as t where meta(t).id ="docId";

		statement.append("select * from `");
		statement.append(bucket.name());
		statement.append("` as t where meta(t).id = \"");
		statement.append(documentId + "\"");
		
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(statement.toString()));

		N1qlQueryRow row = result.allRows().get(0);
		JsonObject content = row.value();
		JSONObject json = null;
		String documentDetails = null;
		
		try {
			json = (JSONObject) parser.parse(content.get("t").toString());
			
			ObjectMapper mapper = new ObjectMapper();
			documentDetails = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return documentDetails;
	}
	
	public Map<String,Object> documentUpsert(HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String documentId = request.getParameter("documentId");
		String documentText = request.getParameter("documentText");
		
		JSONObject obj = (JSONObject) parser.parse(documentText);
		String jsonStr = obj.toString();
		
		JsonObject content = JsonObject.fromJson(jsonStr);
		
		JsonDocument doc = JsonDocument.create(documentId, content); 
		bucket.upsert(doc);
		
		resultMap.put("result", "문서 '"+doc.id() + "' 가 정상적으로 변경되었습니다.");
		
		return resultMap;
	}
	
	public Object dropDocument(String documentId){
		 JsonDocument result = bucket.remove(documentId);
		 
		 System.out.println(result);
		 
		 return "문서가 정상적으로 삭제되었습니다.";
	}
	
	public Object addDocument(HttpServletRequest request){

		String documentId = request.getParameter("documentId");
		String documentText = request.getParameter("documentText");
		
		JSONObject obj;
		try {
			obj = (JSONObject) parser.parse(documentText);
		} catch (ParseException e1) {
			e1.printStackTrace();
			return "JSON 형식이 잘못되었습니다.";
		}
		String jsonStr = obj.toString();
		JsonObject content = JsonObject.fromJson(jsonStr);
		
		try {
			JsonDocument doc = JsonDocument.create(documentId, content); 
			bucket.insert(doc);
		}catch(Exception e) {
			return "동일한 ID 의 Document가 존재합니다.";
		}
		
		return "문서가 생성되었습니다.";
	}

	public Map<String, Object> getQueryResult(HttpServletRequest request) throws Exception {

		String queryInput = request.getParameter("queryInput");
		System.out.println(queryInput);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(queryInput));

		resultMap.put("status", result.status());
		resultMap.put("allRows", result.allRows().toString());
		resultMap.put("error", result.errors().toString());

		return resultMap;
	}
	
	public Object setSettings(SettingDTO settings){
		
		// https://docs.couchbase.com/server/6.5/manage/manage-settings/general-settings.html#configure-general-settings-with-the-rest-api
		
		if(dto == null)
			return null;
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(settings));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		StringBuilder baseCommand = new StringBuilder();
		baseCommand.append("curl -v -X POST -u ");
		baseCommand.append(dto.getStrUserName());
		baseCommand.append(":");
		baseCommand.append(dto.getStrPassword());
		baseCommand.append(" http://");
		baseCommand.append(dto.getStrHostName());
		baseCommand.append(":");
		baseCommand.append(dto.getPortNumber());
		
		StringBuilder clusterCommand = new StringBuilder();
		clusterCommand.append(baseCommand);
		clusterCommand.append("/pools/default -d clusterName=");
		clusterCommand.append(settings.getClusterName());
		clusterCommand.append(" -d memoryQuota=");
		clusterCommand.append(settings.getDataServiceQuota());
		clusterCommand.append(" -d indexMemoryQuota=");
		clusterCommand.append(settings.getIndexServiceQuota());
		clusterCommand.append(" -d ftsMemoryQuota=");
		clusterCommand.append(settings.getSearchServiceQuota());
		clusterCommand.append(" -d cbasMemoryQuota=");
		clusterCommand.append(settings.getAnalyticsServiceQuota());
		clusterCommand.append(" -d eventingMemoryQuota=");
		clusterCommand.append(settings.getEventingServiceQuota());
		System.out.println(clusterCommand);
		
		StringBuilder noticeCommand = new StringBuilder();
		noticeCommand.append(baseCommand);
		noticeCommand.append("/settings/stats -d sendStats=");
		noticeCommand.append(settings.isNoticeUpdate());
		System.out.println(noticeCommand);
		
		StringBuilder nodeCommand = new StringBuilder();
		nodeCommand.append(baseCommand);
		nodeCommand.append("/settings/autoFailover -d enabled=");
		nodeCommand.append(settings.isAutoFailoverCheck());
		nodeCommand.append(" -d timeout=");
		nodeCommand.append(settings.getFailoverSecondTime());
		nodeCommand.append(" -d failoverOnDataDiskIssues[enabled]=");
		nodeCommand.append(settings.isAutoFailoverDataError());
		nodeCommand.append(" -d failoverOnDataDiskIssues[timePeriod]=");
		nodeCommand.append(settings.getAutoFailoverDataErrorSecondTime());
		nodeCommand.append(" -d failoverServerGroup=");
		nodeCommand.append(settings.isAutoFailoverServerGroup());
		nodeCommand.append(" -d maxCount=");
		nodeCommand.append(settings.getXDCRMaximumProcesses());
		nodeCommand.append(" -d canAbortRebalance=");
		nodeCommand.append(settings.isAutoFailoverStopRebalance());
		System.out.println(nodeCommand);
		
		try {
			serviceUtil.curlExcute(clusterCommand.toString());
			serviceUtil.curlExcute(noticeCommand.toString());
			serviceUtil.curlExcute(nodeCommand.toString());
		}
		catch(Exception e) {
			e.printStackTrace();
			return "값이 잘못되었습니다.";
		}
		return "정상적으로 실행되었습니다.";
		
	}

	public Object setCompactions(CompactionDTO compactions){
		
		if(dto == null)
			return null;
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(compactions));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		StringBuilder baseCommand = new StringBuilder();
		baseCommand.append("curl -i -X POST -u ");
		baseCommand.append(dto.getStrUserName());
		baseCommand.append(":");
		baseCommand.append(dto.getStrPassword());
		baseCommand.append(" http://");
		baseCommand.append(dto.getStrHostName());
		baseCommand.append(":");
		baseCommand.append(dto.getPortNumber());
		
		StringBuilder compactionCommand = new StringBuilder();
		compactionCommand.append(baseCommand);
		compactionCommand.append("/controller/setAutoCompaction");
		if(compactions.isFragmentationCheckDatabasePer()) {
			compactionCommand.append(" -d databaseFragmentationThreshold[percentage]=");
			compactionCommand.append(compactions.getFragmentationPercentDatabase());
		}
		if(compactions.isFragmentationCheckDatabaseMB()) {
			compactionCommand.append(" -d databaseFragmentationThreshold[size]=");
			int mb = Integer.parseInt(compactions.getFragmentationMBDatabase());
			compactionCommand.append(mb*1024*1024);
		}
		if(compactions.isFragmentationCheckViewPer()) {
			compactionCommand.append(" -d viewFragmentationThreshold[percentage]=");
			compactionCommand.append(compactions.getFragmentationPercentView());
		}
		if(compactions.isFragmentationCheckViewMB()){
			compactionCommand.append(" -d viewFragmentationThreshold[size]=");
			int mb = Integer.parseInt(compactions.getFragmentationMBView());
			compactionCommand.append(mb*1024*1024);
		}
		if(compactions.isTimeIntervalCheck()) {
			compactionCommand.append(" -d allowedTimePeriod[fromHour]=");
			compactionCommand.append(compactions.getCompactionFromHour());
			compactionCommand.append(" -d allowedTimePeriod[fromMinute]=");
			compactionCommand.append(compactions.getCompactionFromMinute());
			compactionCommand.append(" -d allowedTimePeriod[toHour]=");
			compactionCommand.append(compactions.getCompactionToHour());
			compactionCommand.append(" -d allowedTimePeriod[toMinute]=");
			compactionCommand.append(compactions.getCompactionToMinute());
		}
		
		compactionCommand.append(" -d allowedTimePeriod[abortOutside]=");
		compactionCommand.append(compactions.isAbortCompaction());
		compactionCommand.append(" -d parallelDBAndViewCompaction=");
		compactionCommand.append(compactions.isCompactParallel());
		compactionCommand.append(" -d purgeInteval=");
		compactionCommand.append(compactions.getPurgeInterval());
		System.out.println(compactionCommand);
		
		Map<String,Object> resultMap;
		try {
			resultMap = serviceUtil.curlExcute(compactionCommand.toString());
		}
		catch(Exception e) {
			e.printStackTrace();
			return "오류입니다";
		}
		
		return resultMap.get("result");
	}
}
