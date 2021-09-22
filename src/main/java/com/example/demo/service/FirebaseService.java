package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
//import com.example.demo.model.Products;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {
    public String saveCustomerDetails(Customer customer) throws ExecutionException, InterruptedException, FirebaseAuthException {
        Firestore dbFirestore = FirestoreClient.getFirestore(); //connection to firestore
        ApiFuture<WriteResult> collectionsAPIFuture = dbFirestore.collection("customers").document(customer.getName()).set(customer);

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setUid(customer.getName())
                .setEmail(customer.getEmail())
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setDisabled(false);

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        System.out.println("Successfully created new user: " + userRecord.getUid());
        return "Successfully created new customer : "+collectionsAPIFuture.get().getUpdateTime().toString();
    }

    public Customer getCustomerDetails(String name) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("customers").document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();

        Customer customer = null;
        if(documentSnapshot.exists()){
            customer = documentSnapshot.toObject(Customer.class);
            return customer;
        }else{
            return null;
        }
    }

    public String updateCustomerDetails(Customer customer) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsAPIFuture = dbFirestore.collection("customers").document(customer.getName()).set(customer);
        return "customer updated: "+collectionsAPIFuture.get().getUpdateTime().toString();
    }

    public String deleteCustomer(String name) throws ExecutionException, InterruptedException, FirebaseAuthException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResultApiFuture = dbFirestore.collection("customers").document(name).delete();

        FirebaseAuth.getInstance().deleteUser(name);
        System.out.println("Successfully deleted user.");

        return name + " Successfully Deleted Custoemr"+writeResultApiFuture.get().getUpdateTime();
    }

    //PRODUCT
    public String createProduct(Product product) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsAPIFuture = dbFirestore.collection("products").document(product.getProductName()).set(product);
        return "product created: "+collectionsAPIFuture.get().getUpdateTime().toString();
    }

    //also try getting all products &/ summary
    public Product getProduct(String productName) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("products").document(productName);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();

        Product product = null;
        if(documentSnapshot.exists()){
            product = documentSnapshot.toObject(Product.class);
            return product;
        }else{
            return null;
        }
    }

    public List<Product> getAllProducts() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = dbFirestore.collection("products");
        ApiFuture<QuerySnapshot> future = collectionReference.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Product> list = new ArrayList();
        int count = 0;
        Product products;
        for (QueryDocumentSnapshot document : documents) {
            System.out.println(" hizi hapa " + document.toObject(Product.class));//.getProduct());
            count++;
            System.out.println(" hesabu " + count);

            products = document.toObject(Product.class);
            list.add(products);
           // return products; //document.toObject(Products.class);
        }
        return list;
    }

    //ORDERS
    public String createOrder(Order order) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> collectionsAPIFuture = dbFirestore.collection("orders").document(order.getProductName()).set(order);
        DocumentReference customerReference = dbFirestore.collection("customers").document(order.getOrderedBy());
        ApiFuture<DocumentSnapshot> futureCustomer = customerReference.get();
        DocumentSnapshot customerSnapshot = futureCustomer.get();

        //get amountAvailable from product
        DocumentReference documentReference = dbFirestore.collection("products").document(order.getProductName());
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();

        Product product = null;
        Customer customer2 = null;
        if(documentSnapshot.exists()){ //&&customerSnapshot.exists()
            product = documentSnapshot.toObject(Product.class);
            customer2 = customerSnapshot.toObject(Customer.class);
//            assert product != null;
            assert product != null;
            int amountAvailable = product.getAmountAvailable();
            int orderedAmount = order.getAmountOrdered();
            int remainingAmout;
            if(amountAvailable >= 1) {
                remainingAmout = amountAvailable - orderedAmount;
                if (remainingAmout < orderedAmount) {
                    return "order failed: " + order.getProductName() + " has only "+remainingAmout+" left";
                } else {
                    ApiFuture<WriteResult> collectionsAPIFuture = dbFirestore.collection("products").document(order.getProductName()).update("amountAvailable", remainingAmout);
                    //and set
                    assert customer2 != null;
                    try {
                        ApiFuture<WriteResult> collectionsAPIFutureOrder = dbFirestore.collection("orders").document().set(order); //customer2.getName()
                        return "order made: " + collectionsAPIFutureOrder.get().getUpdateTime().toString();
                    }catch (NullPointerException e){
                     //  System.out.println("nalpointa "+e);
                       return "order made: ";
                    }

                }


            }else{
                return "order failed: " + order.getProductName() + " out of stock";
            }
           // return product;
        }else{
            return "order failed: "+order.getProductName() +" does not exist";
        }

    }

    public Order getOrder(String orderedBy) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("orders").document(orderedBy);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();

        Order order = null;
        if(documentSnapshot.exists()){
            order = documentSnapshot.toObject(Order.class);
            return order;
        }else{
            return null;
        }
    }

}
