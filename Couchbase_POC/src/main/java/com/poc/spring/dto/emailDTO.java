package com.poc.spring.dto;

public class emailDTO {
	
	private boolean emailAlertCheck;
	private String emailServerHost;
	private String emailServerPort;
	private String emailServerUsername;
	private String emailServerPassword;
	private boolean emailTLS;
	private String sender;
	private String alerts;
	private String recipients;
	
	
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public boolean isEmailAlertCheck() {
		return emailAlertCheck;
	}
	public void setEmailAlertCheck(boolean emailAlertCheck) {
		this.emailAlertCheck = emailAlertCheck;
	}
	public String getEmailServerHost() {
		return emailServerHost;
	}
	public void setEmailServerHost(String emailServerHost) {
		this.emailServerHost = emailServerHost;
	}
	public String getEmailServerPort() {
		return emailServerPort;
	}
	public void setEmailServerPort(String emailServerPort) {
		this.emailServerPort = emailServerPort;
	}
	public String getEmailServerUsername() {
		return emailServerUsername;
	}
	public void setEmailServerUsername(String emailServerUsername) {
		this.emailServerUsername = emailServerUsername;
	}
	public String getEmailServerPassword() {
		return emailServerPassword;
	}
	public void setEmailServerPassword(String emailServerPassword) {
		this.emailServerPassword = emailServerPassword;
	}
	public boolean isEmailTLS() {
		return emailTLS;
	}
	public void setEmailTLS(boolean emailTLS) {
		this.emailTLS = emailTLS;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getAlerts() {
		return alerts;
	}
	public void setAlerts(String alerts) {
		this.alerts = alerts;
	}
}
