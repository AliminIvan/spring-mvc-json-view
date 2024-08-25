package com.ivanalimin.spring_mvc_json_view.repository;

import com.ivanalimin.spring_mvc_json_view.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
