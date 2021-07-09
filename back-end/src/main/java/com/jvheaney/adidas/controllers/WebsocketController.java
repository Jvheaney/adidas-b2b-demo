package com.jvheaney.adidas.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jvheaney.adidas.AdidasDemoApplication;
import com.jvheaney.adidas.models.Message;
import com.jvheaney.adidas.models.UsersOrdersAndSuggestions;
import com.jvheaney.adidas.repositories.EntityNodeRepository;

@Controller
@RestController
public class WebsocketController {

	Logger lgr = Logger.getLogger(AdidasDemoApplication.class.getName());

	@Autowired
	EntityNodeRepository enr;

	@Autowired
    private SimpMessagingTemplate template;

	@MessageMapping("/ordering")
    public void placeOrder(org.springframework.messaging.Message<?> msg, @RequestBody Message message) throws Exception {

		//For purposes of the demo, sanitizing the name based on the 4 expected names.
		//You would probably have a redis db to quickly check a customerId existed and was allowed to use this.
		if(!message.getName().equals("Bob") && !message.getName().equals("Alice") && !message.getName().equals("Tommy") && !message.getName().equals("Jenny")) {
			//If it's not one of the expected names, return an error.
			lgr.log(Level.INFO, "[Place Order] Unexpected name entered: " + message.getName());
			return;
		}

		//You would also want to sanitize the SKU, likely with something like redis as well.
		//For the purposes of this demo, we are assuming the SKU isn't tampered with.
		if(message.getAdding()) {
			lgr.log(Level.INFO, "[Place Order] Adding order for customer: " + message.getName() + " and SKU: " + message.getSku());
			enr.placeOrder(message.getName(), message.getSku());
		}
		else {
			lgr.log(Level.INFO, "[Place Order] Removing order for customer: " + message.getName() + " and SKU: " + message.getSku());
			enr.removeOrder(message.getName(), message.getSku());
		}

		//Get all suggestion updates and send to client.
		//In production environments, you probably want to add this to an async message bus like RabbitMQ or Kafka.
		UsersOrdersAndSuggestions uos = new UsersOrdersAndSuggestions();
		uos.setBob(enr.getCustomerSuggestions("Bob"));
		uos.setAlice(enr.getCustomerSuggestions("Alice"));
		uos.setTommy(enr.getCustomerSuggestions("Tommy"));
		uos.setJenny(enr.getCustomerSuggestions("Jenny"));

		Gson gson = new Gson();
		Message suggestions = new Message();
		suggestions.setSuggestions(gson.toJson(uos));

		template.convertAndSend("/suggesting/updates", suggestions);

    }

    @SendTo("/suggesting/updates")
    public Message updateBroker(org.springframework.messaging.Message<?> msg, @RequestBody Message message) throws Exception {
    	//This is the topic clients are subscribed to in order to receive suggestion updates.
		lgr.log(Level.INFO, "[Update Broker] Sending out new suggestions.");
    	return message;
    }

}
