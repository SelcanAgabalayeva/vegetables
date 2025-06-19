package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.CartItemDto;
import itbrains.az.edu.vegetables.dtos.OrderDto;

import itbrains.az.edu.vegetables.models.Cart;
import itbrains.az.edu.vegetables.models.Order;
import itbrains.az.edu.vegetables.models.User;
import itbrains.az.edu.vegetables.repositories.CartRepository;
import itbrains.az.edu.vegetables.repositories.OrderRepository;
import itbrains.az.edu.vegetables.repositories.UserRepository;
import itbrains.az.edu.vegetables.services.CartService;
import itbrains.az.edu.vegetables.services.OrderService;
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

    }

    @Override
    public Order getLatestOrder() {
        Order latest = orderRepository.findTopByOrderByIdDesc();
        System.out.println("Latest Order: " + latest);
        return latest;
    }


}
