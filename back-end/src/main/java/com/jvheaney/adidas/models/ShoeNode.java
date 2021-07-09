package com.jvheaney.adidas.models;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;

/*
 * This is not used directly by this application.
 * I added this to demonstrate the properties nodes in the graph may have.
 * In a production environment, this would be called to create new shoes nodes within the graph.
 */

public class ShoeNode {

	@Id @GeneratedValue
	private Long id;
	
	@Property("name")
	private String name;
	
	@Property("sku")
	private String sku;
	
	public ShoeNode() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	
}
