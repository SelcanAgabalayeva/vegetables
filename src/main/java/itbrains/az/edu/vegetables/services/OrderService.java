package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.OrderDto;

import itbrains.az.edu.vegetables.models.Order;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {


    void createOrder(@Valid OrderDto dto, String name);

    Order getLatestOrder();

}
