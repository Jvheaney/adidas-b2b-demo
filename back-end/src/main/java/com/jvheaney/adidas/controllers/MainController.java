package com.jvheaney.adidas.controllers;
import com.google.gson.Gson;
import com.jvheaney.adidas.AdidasDemoApplication;
import com.jvheaney.adidas.models.Response;
import com.jvheaney.adidas.models.UsersOrdersAndSuggestions;
import com.jvheaney.adidas.repositories.EntityNodeRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class MainController {
	
	@Autowired
	EntityNodeRepository enr;
	
	Logger lgr = Logger.getLogger(AdidasDemoApplication.class.getName());
	
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ResponseEntity < String > getOrders(HttpServletRequest request) {
		Gson gson = new Gson();
		Response response = new Response();
    	
		try {
			//Here we will call on Neo4J to handle the fetching of the all the user's orders.
			UsersOrdersAndSuggestions uos = new UsersOrdersAndSuggestions();
			
			/*  
			 * We will be calling each customer individually to replicate how a sales interface would likely be used.
			 * Sales/Marketing staff likely are not wanting to query all of their customers at once, just a few top customers.
			 * In the event that they do want to query them all, we'd create another Cypher query to combine results 
			 * for each customer into a single node entity.
			*/
			
			uos.setBob(enr.getCustomerOrders("Bob"));
			uos.setAlice(enr.getCustomerOrders("Alice"));
			uos.setTommy(enr.getCustomerOrders("Tommy"));
			uos.setJenny(enr.getCustomerOrders("Jenny"));
			
			response.setOK(true);
			response.setStatus("Success.");
			response.setData(gson.toJson(uos));
			lgr.log(Level.INFO, "[Get Orders] Successfully returned orders.");
			return new ResponseEntity<String>(gson.toJson(response), HttpStatus.OK);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setOK(false);
			response.setStatus("Error: Persistence error.");
			lgr.log(Level.SEVERE, "[Get Orders] Persistence error.");
			return new ResponseEntity<String>(gson.toJson(response), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/suggestions", method = RequestMethod.GET)
    public ResponseEntity < String > getSuggestions(HttpServletRequest request) {
		Gson gson = new Gson();
		Response response = new Response();
    	
		try {
			//Here we will call on Neo4J to handle the fetching of the all the user's suggestions.
			UsersOrdersAndSuggestions uos = new UsersOrdersAndSuggestions();
			
			/*  
			 * We will be calling each customer individually to replicate how a sales interface would likely be used.
			 * Sales/Marketing staff likely are not wanting to query all of their customers at once, just a few top customers.
			 * In the event that they do want to query them all, we'd create another Cypher query to combine results 
			 * for each customer into a single node entity.
			*/
			
			uos.setBob(enr.getCustomerSuggestions("Bob"));
			uos.setAlice(enr.getCustomerSuggestions("Alice"));
			uos.setTommy(enr.getCustomerSuggestions("Tommy"));
			uos.setJenny(enr.getCustomerSuggestions("Jenny"));
			
			response.setOK(true);
			response.setStatus("Success.");
			response.setData(gson.toJson(uos));
			lgr.log(Level.INFO, "[Get Suggestions] Successfully returned suggestions.");
			return new ResponseEntity<String>(gson.toJson(response), HttpStatus.OK);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setOK(false);
			response.setStatus("Error: Persistence error.");
			lgr.log(Level.SEVERE, "[Get Suggestions] Persistence error.");
			return new ResponseEntity<String>(gson.toJson(response), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
