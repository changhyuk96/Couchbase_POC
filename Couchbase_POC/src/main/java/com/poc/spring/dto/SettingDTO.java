package com.poc.spring.dto;

public class SettingDTO {
	
	private String clusterName;
	private String dataServiceQuota;
	private String indexServiceQuota;
	private String searchServiceQuota;
	private String analyticsServiceQuota;
	private String eventingServiceQuota;
	private boolean noticeUpdate;
	private boolean autoFailoverCheck;
	private String failoverSecondTime;
	private String failoverEvent;
	private boolean autoFailoverDataError;
	private String autoFailoverDataErrorSecondTime;
	private boolean autoFailoverServerGroup;
	private boolean autoFailoverStopRebalance;
	private String autoReprovisioningNode;
	private boolean autoReprovisioning;
	private String readThread;
	private String readThreadNumber;
	private String writeThread;
	private String writeThreadNumber;
	private String indexStorageMode;
	private String indexerThreadNumber;
	private String logLevel;
	private String XDCRMaximumProcesses;
	
	
	public boolean isNoticeUpdate() {
		return noticeUpdate;
	}
	public void setNoticeUpdate(boolean noticeUpdate) {
		this.noticeUpdate = noticeUpdate;
	}
	public String getReadThread() {
		return readThread;
	}
	public void setReadThread(String readThread) {
		this.readThread = readThread;
	}
	public String getReadThreadNumber() {
		return readThreadNumber;
	}
	public void setReadThreadNumber(String readThreadNumber) {
		this.readThreadNumber = readThreadNumber;
	}
	public String getWriteThread() {
		return writeThread;
	}
	public void setWriteThread(String writeThread) {
		this.writeThread = writeThread;
	}
	public String getWriteThreadNumber() {
		return writeThreadNumber;
	}
	public void setWriteThreadNumber(String writeThreadNumber) {
		this.writeThreadNumber = writeThreadNumber;
	}
	public String getIndexStorageMode() {
		return indexStorageMode;
	}
	public void setIndexStorageMode(String indexStorageMode) {
		this.indexStorageMode = indexStorageMode;
	}
	public String getIndexerThreadNumber() {
		return indexerThreadNumber;
	}
	public void setIndexerThreadNumber(String indexerThreadNumber) {
		this.indexerThreadNumber = indexerThreadNumber;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getXDCRMaximumProcesses() {
		return XDCRMaximumProcesses;
	}
	public void setXDCRMaximumProcesses(String xDCRMaximumProcesses) {
		XDCRMaximumProcesses = xDCRMaximumProcesses;
	}
	public String getAutoReprovisioningNode() {
		return autoReprovisioningNode;
	}
	public void setAutoReprovisioningNode(String autoReprovisioningNode) {
		this.autoReprovisioningNode = autoReprovisioningNode;
	}
	
	public boolean isAutoReprovisioning() {
		return autoReprovisioning;
	}
	public void setAutoReprovisioning(boolean autoReprovisioning) {
		this.autoReprovisioning = autoReprovisioning;
	}
	public boolean isAutoFailoverStopRebalance() {
		return autoFailoverStopRebalance;
	}
	public void setAutoFailoverStopRebalance(boolean autoFailoverStopRebalance) {
		this.autoFailoverStopRebalance = autoFailoverStopRebalance;
	}
	public boolean isAutoFailoverCheck() {
		return autoFailoverCheck;
	}
	public void setAutoFailoverCheck(boolean autoFailoverCheck) {
		this.autoFailoverCheck = autoFailoverCheck;
	}
	public String getFailoverSecondTime() {
		return failoverSecondTime;
	}
	public void setFailoverSecondTime(String failoverSecondTime) {
		this.failoverSecondTime = failoverSecondTime;
	}
	public String getFailoverEvent() {
		return failoverEvent;
	}
	public void setFailoverEvent(String failoverEvent) {
		this.failoverEvent = failoverEvent;
	}
	public boolean isAutoFailoverDataError() {
		return autoFailoverDataError;
	}
	public void setAutoFailoverDataError(boolean autoFailoverDataError) {
		this.autoFailoverDataError = autoFailoverDataError;
	}
	public String getAutoFailoverDataErrorSecondTime() {
		return autoFailoverDataErrorSecondTime;
	}
	public void setAutoFailoverDataErrorSecondTime(String autoFailoverDataErrorSecondTime) {
		this.autoFailoverDataErrorSecondTime = autoFailoverDataErrorSecondTime;
	}
	public boolean isAutoFailoverServerGroup() {
		return autoFailoverServerGroup;
	}
	public void setAutoFailoverServerGroup(boolean autoFailoverServerGroup) {
		this.autoFailoverServerGroup = autoFailoverServerGroup;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public String getDataServiceQuota() {
		return dataServiceQuota;
	}
	public void setDataServiceQuota(String dataServiceQuota) {
		this.dataServiceQuota = dataServiceQuota;
	}
	public String getIndexServiceQuota() {
		return indexServiceQuota;
	}
	public void setIndexServiceQuota(String indexServiceQuota) {
		this.indexServiceQuota = indexServiceQuota;
	}
	public String getSearchServiceQuota() {
		return searchServiceQuota;
	}
	public void setSearchServiceQuota(String searchServiceQuota) {
		this.searchServiceQuota = searchServiceQuota;
	}
	public String getAnalyticsServiceQuota() {
		return analyticsServiceQuota;
	}
	public void setAnalyticsServiceQuota(String analyticsServiceQuota) {
		this.analyticsServiceQuota = analyticsServiceQuota;
	}
	public String getEventingServiceQuota() {
		return eventingServiceQuota;
	}
	public void setEventingServiceQuota(String eventingServiceQuota) {
		this.eventingServiceQuota = eventingServiceQuota;
	}
}
