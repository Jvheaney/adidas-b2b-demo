package com.jvheaney.adidas.models;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/*
 * This is not used directly by this application.
 * I added this to demonstrate the properties nodes in the graph may have.
 * In a production environment, this would be called to create new interest nodes within the graph.
 */

@NodeEntity
public class InterestNode {

	@Id @GeneratedValue
	private Long id;
	
	@Property("type")
	private String type;
	
	public InterestNode() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
