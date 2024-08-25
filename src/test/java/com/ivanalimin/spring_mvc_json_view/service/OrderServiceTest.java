package com.ivanalimin.spring_mvc_json_view.service;

import com.ivanalimin.spring_mvc_json_view.exception_handling.NotFoundException;
import com.ivanalimin.spring_mvc_json_view.model.Order;
import com.ivanalimin.spring_mvc_json_view.model.OrderStatus;
import com.ivanalimin.spring_mvc_json_view.model.User;
import com.ivanalimin.spring_mvc_json_view.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testFindAll() {
        Order order1 = new Order(UUID.randomUUID(), BigDecimal.valueOf(100.00), OrderStatus.IN_PROGRESS, List.of("Product1", "Product2"), null);
        Order order2 = new Order(UUID.randomUUID(), BigDecimal.valueOf(200.00), OrderStatus.COMPLETED, List.of("Product3", "Product4"), null);

        List<Order> orders = Arrays.asList(order1, order2);

        given(orderRepository.findAll()).willReturn(orders);

        List<Order> result = orderService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(order1.getAmount(), result.get(0).getAmount());
        assertEquals(order2.getStatus(), result.get(1).getStatus());
    }

    @Test
    void testFindById() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, BigDecimal.valueOf(100.00), OrderStatus.IN_PROGRESS, List.of("Product1", "Product2"), null);

        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));

        Order result = orderService.findById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
    }

    @Test
    void testFindById_OrderNotFound() {
        UUID orderId = UUID.randomUUID();

        given(orderRepository.findById(orderId)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.findById(orderId));
    }

    @Test
    void testSave() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "John Doe", "john.doe@example.com", new HashSet<>());
        Order order = new Order(null, BigDecimal.valueOf(100.00), OrderStatus.IN_PROGRESS, List.of("Product1", "Product2"), user);

        given(userService.findById(userId)).willReturn(user);
        given(orderRepository.save(order)).willReturn(order);

        Order result = orderService.save(userId, order);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
        assertEquals(OrderStatus.IN_PROGRESS, result.getStatus());
        assertEquals(user, result.getUser());
    }

    @Test
    void testUpdate() {
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        User user = new User(userId, "John Doe", "john.doe@example.com", new HashSet<>());
        Order existingOrder = new Order(orderId, BigDecimal.valueOf(100.00), OrderStatus.IN_PROGRESS, List.of("Product1", "Product2"), user);
        Order updatedOrder = new Order(orderId, BigDecimal.valueOf(200.00), OrderStatus.COMPLETED, List.of("Product3", "Product4"), user);

        given(userService.findById(userId)).willReturn(user);
        given(orderRepository.findById(orderId)).willReturn(Optional.of(existingOrder));
        given(orderRepository.save(existingOrder)).willReturn(updatedOrder);

        Order result = orderService.update(userId, orderId, updatedOrder);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(200.00), result.getAmount());
        assertEquals(OrderStatus.COMPLETED, result.getStatus());
        assertEquals(List.of("Product3", "Product4"), result.getProducts());
    }

    @Test
    void testUpdate_OrderNotFound() {
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Order updatedOrder = new Order(orderId, BigDecimal.valueOf(200.00), OrderStatus.COMPLETED, List.of("Product3", "Product4"), null);

        given(userService.findById(userId)).willReturn(new User(userId, "John Doe", "john.doe@example.com", new HashSet<>()));
        given(orderRepository.findById(orderId)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.update(userId, orderId, updatedOrder));
    }

    @Test
    void testDeleteById() {
        UUID orderId = UUID.randomUUID();

        orderService.deleteById(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
