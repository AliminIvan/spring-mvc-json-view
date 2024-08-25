package com.ivanalimin.spring_mvc_json_view.repository;

import com.ivanalimin.spring_mvc_json_view.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
