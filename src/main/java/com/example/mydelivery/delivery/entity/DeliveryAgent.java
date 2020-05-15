package com.example.mydelivery.delivery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "delivery_agents")
public class DeliveryAgent {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "agent_name")
    private String agentName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "order_id")
    private String orderId;

    public String getId(){
        return id;
    }

    public Boolean getIsActive(){
        return isActive;
    }

    public String getOrderId(){
        return orderId;
    }
}
