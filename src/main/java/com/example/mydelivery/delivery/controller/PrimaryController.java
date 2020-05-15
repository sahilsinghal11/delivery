package com.example.mydelivery.delivery.controller;

import com.example.mydelivery.delivery.service.PrimaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrimaryController {

    @Autowired
    PrimaryService primaryService;

    @RequestMapping(value = "/delegate_order", method = RequestMethod.POST)
    public HashMap<String, Object> delegateOrder(@RequestParam(value = "order_id") String orderId){
        return primaryService.delegateOrder(orderId);
    }

    @RequestMapping(value = "/get_agent_status", method = RequestMethod.GET)
    public HashMap<String, Object> getAgentStatus(@RequestParam(value = "agent_id") String agentId){
        return primaryService.getAgentStatus(agentId);
    }
}
