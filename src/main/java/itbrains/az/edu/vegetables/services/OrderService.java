package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.order.CartItemDto;
import itbrains.az.edu.vegetables.dtos.order.OrderDto;

import itbrains.az.edu.vegetables.dtos.order.OrderDashboardDto;
import itbrains.az.edu.vegetables.dtos.order.OrderStatus;
import itbrains.az.edu.vegetables.models.Order;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {


    void createOrder(@Valid OrderDto dto, String name);

    Order getLatestOrder();

    List<OrderDashboardDto> findAllOrders();

    void updateStatus(Long id, OrderStatus status);

    List<CartItemDto> getOrderById(Long id);
}
