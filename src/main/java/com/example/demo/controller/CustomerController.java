package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.service.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class CustomerController {
    @Autowired
    FirebaseService firebaseService;

    @GetMapping("/getCustomerDetails")
    public Customer getCustomerDetails(@RequestHeader String name) throws ExecutionException, InterruptedException {
        return firebaseService.getCustomerDetails(name);
    }

    @PostMapping("/createCustomer")
    public String createCustomer(@RequestBody Customer customer) throws ExecutionException, InterruptedException, FirebaseAuthException {
        return firebaseService.saveCustomerDetails(customer);
    }

    @PutMapping("/updateCustomer")
    public String updateCustomer(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return firebaseService.updateCustomerDetails(customer);
    }

    @DeleteMapping("/deleteCustomer")
    public String deleteCustomer(@RequestHeader String name) throws ExecutionException, InterruptedException, FirebaseAuthException {
        return firebaseService.deleteCustomer(name);
    }


}
