package com.ivanalimin.spring_mvc_json_view.service;

import com.ivanalimin.spring_mvc_json_view.exception_handling.NotFoundException;
import com.ivanalimin.spring_mvc_json_view.model.Order;
import com.ivanalimin.spring_mvc_json_view.model.User;
import com.ivanalimin.spring_mvc_json_view.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Order with id " + id + " not found"));
    }

    @Transactional
    public Order save(UUID userId, Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        User user = userService.findById(userId);
        order.setUser(user);
        user.getOrders().add(order);
        return orderRepository.save(order);
    }

    @Transactional
    public Order update(UUID userId, UUID orderId, Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        User user = userService.findById(userId);
        Order orderToUpdate = findById(orderId);
        if (!user.getId().equals(orderToUpdate.getUser().getId())) {
            throw new IllegalArgumentException("This order does not belong to the specified user");
        }
        orderToUpdate.setStatus(order.getStatus());
        orderToUpdate.setAmount(order.getAmount());
//        orderToUpdate.setProducts(order.getProducts());
        return orderRepository.save(orderToUpdate);
    }

    @Transactional
    public void deleteById(UUID id) {
        orderRepository.deleteById(id);
    }
}
