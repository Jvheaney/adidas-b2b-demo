package com.jvheaney.adidas.models;

public class Response {
	private String status;
	private Boolean OK;
	private String data;
	
	public Response() {
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getOK() {
		return OK;
	}

	public void setOK(Boolean oK) {
		OK = oK;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	

}
