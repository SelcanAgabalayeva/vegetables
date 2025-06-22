package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.order.CartItemDto;
import itbrains.az.edu.vegetables.dtos.order.OrderDto;

import itbrains.az.edu.vegetables.dtos.order.OrderDashboardDto;
import itbrains.az.edu.vegetables.dtos.order.OrderStatus;
import itbrains.az.edu.vegetables.models.Cart;
import itbrains.az.edu.vegetables.models.Order;
import itbrains.az.edu.vegetables.models.User;
import itbrains.az.edu.vegetables.repositories.CartRepository;
import itbrains.az.edu.vegetables.repositories.OrderRepository;
import itbrains.az.edu.vegetables.repositories.UserRepository;
import itbrains.az.edu.vegetables.services.CartService;
import itbrains.az.edu.vegetables.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    public OrderServiceImpl(UserRepository userRepository, CartService cartService, OrderRepository orderRepository, CartRepository cartRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;

        this.modelMapper = modelMapper;
    }


    @Override
    public void createOrder(OrderDto dto, String name) {
        User user = userRepository.findByUsername(name);


        List<CartItemDto> cartItems = cartService.findItems(name);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        Order order = new Order();
        order.setFirstName(dto.getFirstName());
        order.setLastName(dto.getLastName());
        order.setOrderNotes(dto.getOrderNotes());
        order.setAddressLine(dto.getAddressLine());
        order.setCity(dto.getCity());
        order.setCountry(dto.getCountry());
        order.setPostcode(dto.getPostcode());
        order.setMobile(dto.getMobile());
        order.setEmail(dto.getEmail());
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setCreateAccount(dto.getCreateAccount());
        order.setShipToDifferent(dto.getShipToDifferent());

        orderRepository.save(order);
        List<Cart> carts = cartRepository.findByUser(user);

        for (Cart cart : carts) {
            cart.setOrder(order);
            cartRepository.save(cart);
        }

    }

    @Override
    public Order getLatestOrder() {
        Order latest = orderRepository.findTopByOrderByIdDesc();
        System.out.println("Latest Order: " + latest);
        return latest;
    }

    @Override
    public List<OrderDashboardDto> findAllOrders() {
        List<OrderDashboardDto> orders = orderRepository.findAll().stream().map(x->modelMapper.map(x,OrderDashboardDto.class)).collect(Collectors.toList());
        return orders;
    }

    @Override
    public void updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public List<CartItemDto> getOrderById(Long id) {
        List<Cart> orderItems = cartRepository.findByOrder_Id(id);
        List<CartItemDto> orderDetailDto = orderItems.stream()
                .map(item -> {
                    CartItemDto dto = new CartItemDto();
                    dto.setId(item.getId());
                    dto.setProductId(item.getProduct().getId());
                    dto.setName(item.getName());
                    dto.setImageUrl(item.getImageUrl());
                    dto.setQuantity(item.getQuantity());
                    dto.setPrice(item.getPrice());
                    return dto;
                }).toList();

        return orderDetailDto;
    }


}
