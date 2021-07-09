package com.jvheaney.adidas.models;

public class Message {
	private String name;
	private String sku;
	private Boolean adding;
	private String suggestions;
	
	public Message() {
		
	}
	
	public Message(String name, String sku, Boolean adding) {
		this.name = name;
		this.sku = sku;
		this.adding = adding;
	}
	
	public Message(String name, String sku, Boolean adding, String suggestions) {
		this.name = name;
		this.sku = sku;
		this.adding = adding;
		this.suggestions = suggestions;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public Boolean getAdding() {
		return adding;
	}

	public void setAdding(Boolean adding) {
		this.adding = adding;
	}
	
	public String getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}
	
	
}
