package org.example.lab7_inventory.controller;

import org.example.lab7_inventory.Constants;
import org.example.lab7_inventory.entity.OrderStatus;
import org.example.lab7_inventory.entity.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryController {
    private final List<Product> products = new ProductList().initializeProducts();

    @RabbitListener(queues = Constants.QUEUE )
    public void consumeMessageFromQueue(OrderStatus orderStatus) {
        System.out.println("Message Received from queue: " +orderStatus );
        boolean found=false;
        for(Product product: products) {
            if(product.getProductId().equals(orderStatus.getProductId())) {
                if(product.getQty() >= orderStatus.getOrder().getQty()) {
                    product.setQty(product.getQty() - orderStatus.getOrder().getQty());
                    System.out.println(orderStatus.getMessage());
                    System.out.println(product.getQty());
                    found=true;
                } else {
                    System.out.println("Order failed");
                }
            }
        }
        if(!found){
            System.out.println("Product not found");
        }

    }
}