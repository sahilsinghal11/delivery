package com.example.mydelivery.delivery.service;

import com.example.mydelivery.delivery.entity.DeliveryAgent;
import com.example.mydelivery.delivery.repository.DeliveryAgentRepository;
import com.example.mydelivery.delivery.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
public class PrimaryService {

    @Value(("${external.restaurant.base.url}"))
    String restaurantBaseUrl;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DeliveryAgentRepository deliveryAgentRepository;

    public HashMap<String, Object> delegateOrder(String orderId){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", false);
        response.put("message", "success");
        HashMap<String, Object> data = new HashMap<>();

        List<DeliveryAgent> deliveryAgentList = deliveryAgentRepository.findByIsActive(false);
        if(deliveryAgentList.isEmpty()){
            response.put("error", true);
            response.put("message", "All agents are busy righht now");
            return response;
        }

        try{
            deliveryAgentRepository.updateOrderId(deliveryAgentList.get(0).getId(), orderId);
        }
        catch(Exception e){
            response.put("error", true);
            response.put("message", e.getMessage());
            return response;
        }

        data.put("agent_id", deliveryAgentList.get(0).getId());
        response.put("data", data);
        return response;
    }

    public HashMap<String, Object> getAgentStatus(String agentId){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", false);
        response.put("message", "success");
        HashMap<String, Object> data = new HashMap<>();

        List<DeliveryAgent> deliveryAgents = deliveryAgentRepository.findById(agentId);
        if(deliveryAgents.isEmpty()){
            response.put("error", true);
            response.put("message", "agent does not exist");
            return response;
        }

        Boolean isActive = deliveryAgents.get(0).getIsActive();
        if(!isActive){
            data.put("is_active", false);
            response.put("data", data);
            return response;
        }
        else{
            data.put("is_active", true);
            String orderId = deliveryAgents.get(0).getOrderId();
            String url = restaurantBaseUrl + "get_order_status?order_id=" + orderId;
            GenericResponse apiResponse = restTemplate.getForObject(url, GenericResponse.class);
            data.put("order_id", orderId);
            data.put("status", apiResponse.getData().get("status"));
        }

        response.put("data", data);
        return response;
    }
}
