package com.jvheaney.adidas.models;

import java.util.List;

public class UsersOrdersAndSuggestions {
	private List<String> Bob;
	private List<String> Alice;
	private List<String> Tommy;
	private List<String> Jenny;
	//We broke Java convention above to match the expected JS format.
	
	public UsersOrdersAndSuggestions() {
		
	}

	public List<String> getBob() {
		return Bob;
	}

	public void setBob(List<String> bob) {
		this.Bob = bob;
	}

	public List<String> getAlice() {
		return Alice;
	}

	public void setAlice(List<String> alice) {
		this.Alice = alice;
	}

	public List<String> getTommy() {
		return Tommy;
	}

	public void setTommy(List<String> tommy) {
		this.Tommy = tommy;
	}

	public List<String> getJenny() {
		return Jenny;
	}

	public void setJenny(List<String> jenny) {
		this.Jenny = jenny;
	}
	
	
}
