package com.example.mydelivery.delivery.repository;

import com.example.mydelivery.delivery.entity.DeliveryAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent, Long> {

    List<DeliveryAgent> findByIsActive(Boolean isActive);

    List<DeliveryAgent> findById(String id);

    @Modifying
    @Query("update DeliveryAgent d set d.orderId = :orderId where d.agentId = :agentId")
    void updateOrderId(@Param("agentId") String agentId,
                       @Param("orderId") String orderId);
}
