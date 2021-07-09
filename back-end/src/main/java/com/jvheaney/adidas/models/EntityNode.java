package com.jvheaney.adidas.models;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/*
 * This is not used directly by this application.
 * I added this to demonstrate the properties nodes in the graph may have.
 * In a production environment, this would be called to create new customer/company nodes within the graph.
 */

@NodeEntity
public class EntityNode {
	
	@Id @GeneratedValue
	private Long id;
	
	@Property("name")
	private String name;
	
	@Property("isCompany")
	private Boolean isCompany;
	
	public EntityNode() {
		
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

	public Boolean getIsCompany() {
		return isCompany;
	}

	public void setIsCompany(Boolean isCompany) {
		this.isCompany = isCompany;
	}
	
	
}
