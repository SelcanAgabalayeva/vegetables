package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.OrderDto;
import jakarta.validation.Valid;

public interface OrderService {


    void createOrder(@Valid OrderDto dto, String name);

}
