package org.example.lab7.controller;

import org.example.lab7.Constants;
import org.example.lab7.entity.Order;
import org.example.lab7.entity.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String restaurantName ) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "Order Successfully Placed to " + restaurantName, "123");
        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY, orderStatus);
        return "success!!";
    }
}