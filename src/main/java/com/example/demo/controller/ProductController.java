package com.example.demo.controller;

import com.example.demo.model.Product;
//import com.example.demo.model.Products;
import com.example.demo.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class ProductController {
    @Autowired
    FirebaseService firebaseService;

    @PostMapping("/createProduct")
    public String createProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {
        return firebaseService.createProduct(product);
    }

    @GetMapping("/getProduct")
    public Product getProduct(@RequestHeader String productName) throws ExecutionException, InterruptedException {
        return firebaseService.getProduct(productName);
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() throws ExecutionException, InterruptedException {
        return firebaseService.getAllProducts();
    }


}
