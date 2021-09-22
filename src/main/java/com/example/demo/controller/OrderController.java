package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class OrderController {
    @Autowired
    FirebaseService firebaseService;

    @PostMapping("/createOrder")
    public String createOrder(@RequestBody Order order) throws ExecutionException, InterruptedException {
        return firebaseService.createOrder(order);
    }

    @GetMapping("/getOrder")
    public Order getOrder(@RequestHeader String orderedBy) throws ExecutionException, InterruptedException {
        return firebaseService.getOrder(orderedBy);
    }

}
