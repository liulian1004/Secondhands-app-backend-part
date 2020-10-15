/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import secondHandMarket.model.ProductOrder;
import secondHandMarket.model.Product;
import secondHandMarket.model.User;
import secondHandMarket.service.ProductOrderService;
import secondHandMarket.service.ProductService;
import secondHandMarket.service.UserService;

import java.util.*;

/**
 * Controller class for product orders.
 */
@RestController
@RequestMapping("/orders")
public class ProductOrderController {

    private final UserService userService;
    private final ProductService productService;
    private final ProductOrderService productOrderService;
    private List<ProductOrder> productOrders;
    private List<User> users;
    private HttpHeaders responseHeaders;

    public ProductOrderController(UserService userService,
                                  ProductService productService,
                                  ProductOrderService productOrderService) {
        this.userService = userService;
        this.productService = productService;
        this.productOrderService = productOrderService;
        this.responseHeaders = new HttpHeaders();
    }

    /**
     * Controller method for sale history, containing active and no-active orders.
     *
     * @param id_token string with id_token for authentication, here must match with seller userUID
     * @return HTTP response entity with orders created by the seller given
     */
    @GetMapping(value = "/seller")
    public ResponseEntity<Map<String, Object>> getOrderHistory(
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        users = userService.getAllUsers();
        Map<String, Object> response = new LinkedHashMap<>();
        String uid = verifyToken(id_token);
        for (User seller : users) {
            if (seller.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: Seller %s is authorized.\n", uid);
                productOrders = productOrderService.getOrders();
                List<ProductOrder> orderHistory = new ArrayList<>();
                for (ProductOrder productOrder : productOrders) {
                    if (productOrder.getProduct().getUser().getUserUID().equals(uid)) {
                        orderHistory.add(productOrder);
                    }
                }

                LinkedHashMap<ProductOrder, Integer> sellerOrderHistoryMap = new LinkedHashMap<>();
                int timePenalty = 0;
                int confirmationPenalty = orderHistory.size();
                int ratingPenalty = orderHistory.size();
                for (ProductOrder productOrder: orderHistory) {
                    if (productOrder.isConfirmed() && productOrder.getRating() == 0.0) {
                        sellerOrderHistoryMap.put(productOrder, 0 + timePenalty);
                    } else if (!productOrder.isConfirmed()){
                        sellerOrderHistoryMap.put(productOrder, confirmationPenalty + timePenalty);
                    } else {
                        sellerOrderHistoryMap.put(productOrder, confirmationPenalty + ratingPenalty + timePenalty);
                    }
                    timePenalty++;
                }

                sellerOrderHistoryMap = sortByValue(sellerOrderHistoryMap, Comparator.comparingInt(o -> o));

                responseHeaders.set("status", "Seller order history rendered successfully:)");
                response.put("status", "OK");
                response.put("summary", String.format("%d orders found", orderHistory.size()));
                response.put("orders", new ArrayList<>(sellerOrderHistoryMap.keySet()));

                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
            }
        }

        responseHeaders.set("status", "SELLER IS NOT AUTHORIZED TO VIEW ORDERS:(");
        response.put("status", "Seller not authorized");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Controller method for order history, containing active and no-active orders for customer.
     *
     * @param id_token string with id_token for authentication, here must match with customer userUID
     * @return HTTP response entity with orders for the customer entry given
     */
    @GetMapping("/customer")
    public ResponseEntity<Map<String, Object>> getPurchaseHistory(
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        Map<String, Object> response = new LinkedHashMap<>();
        String uid = verifyToken(id_token);
        List<User> users = userService.getAllUsers();
        for (User buyer : users) {
            if (buyer.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: Buyer %s is authorized.\n", uid);
                List<ProductOrder> productOrders = productOrderService.getOrders();
                List<ProductOrder> orderHistory = new ArrayList<>();
                for (ProductOrder productOrder : productOrders) {
                    if (productOrder.getCustomer().getUserUID().equals(uid)) {
                        orderHistory.add(productOrder);
                    }
                }

                LinkedHashMap<ProductOrder, Integer> customerOrderHistoryMap = new LinkedHashMap<>();
                int timePenalty = 0;
                int confirmationPenalty = orderHistory.size();
                for (ProductOrder productOrder: orderHistory) {
                    if (!productOrder.isConfirmed()) {
                        customerOrderHistoryMap.put(productOrder, 0 + timePenalty);
                    } else {
                        customerOrderHistoryMap.put(productOrder, confirmationPenalty + timePenalty);
                    }
                    timePenalty++;
                }

                customerOrderHistoryMap = sortByValue(customerOrderHistoryMap, Comparator.comparingInt(o -> o));

                responseHeaders.set("status", "Customer order history rendered successfully:)");
                response.put("status", "OK");
                response.put("summary", String.format("%d orders found", orderHistory.size()));
                response.put("orders", new ArrayList<>(customerOrderHistoryMap.keySet()));

                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
            }
        }

        responseHeaders.set("status", "CUSTOMER IS NOT AUTHORIZED TO VIEW ORDERS:(");
        response.put("status", "Customer not authorized");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Controller method for creating a product order item and save to the database.
     * Accessible for sellers only (seller user entity accessible through the product object)
     *
     * @param customer_id string with customer_id for authentication, here must match with seller userUID
     * @return HTTP response entity with created product order entry
     */
    @PostMapping("/seller")
    public ResponseEntity<Map<String, Object>> createOrder(
            @RequestParam(value = "customer_id", defaultValue = "N/A") String customer_id,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token,
            @RequestParam(value = "product_id", defaultValue = "N/A") long product_id) {
        Map<String, Object> response = new LinkedHashMap<>();
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();

        for (User user : users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: Seller %s is authorized.\n", uid);
                Product product = productService.getProductById(product_id);
                if (product.getUser().getUserUID().equals(uid)) {
                    User customer = userService.getUserById(customer_id);
                    ProductOrder newProductOrder = new ProductOrder(product, customer);
                    ProductOrder ProductOrderSaveResponse = productOrderService.save(newProductOrder);

                    if (ProductOrderSaveResponse != null) {
                        responseHeaders.set("status", "PRODUCT ORDER SUCCESSFULLY CREATED AND SAVED:)");
                        response.put("status", "OK");
                        response.put("order", ProductOrderSaveResponse);
                        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                    } else {
                        responseHeaders.set("status", "Unexpected Error:(");
                        response.put("status", "ERROR");
                        response.put("description", "Unexpected Error");
                        return new ResponseEntity<>(response, responseHeaders, HttpStatus.EXPECTATION_FAILED);
                    }
                } else {
                    responseHeaders.set("status", "SELLER CAN ONLY CREATE ORDERS FOR HIS/HER OWN PRODUCTS:(");
                    response.put("status", "FORBIDDEN");
                    response.put("description", "Seller doesn't own the product.");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.FORBIDDEN);
                }
            }
        }

        responseHeaders.set("status", "SELLER IS NOT AUTHORIZED TO CREATE ORDERS:(");
        response.put("status", "Seller not authorized");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given customer_id exists in the backend database.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Controller method for updating a product order item for fields excluding <code>rating</code>,
     * accessible for seller only.
     * @param id_token string with id_token for authentication, here must match with seller userUID
     * @return HTTP response entity with updated product order entry
     */
    @PutMapping("/seller/order/{orderId}")
    public ResponseEntity<Map<String, Object>> updateOrder(
            @PathVariable Long orderId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        Map<String, Object> response = new LinkedHashMap<>();
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();

        for (User user : users) {
            if (user.getUserUID().equals(uid)) {
                ProductOrder productOrder = productOrderService.getOrderById(orderId);
                if (productOrder.getProduct().getUser().getUserUID().equals(uid)) {
                    if (productOrder != null) {
                        productOrder.setConfirmed(true);
                        responseHeaders.set("status", "PRODUCT ORDER SUCCESSFULLY UPDATED:)");
                        response.put("status", "OK");
                        response.put("product_order", productOrderService.save(productOrder));
                        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                    } else {
                        responseHeaders.set("status", "Unexpected Error:(");
                        response.put("status", "ERROR");
                        response.put("description", "Unexpected Error");
                        return new ResponseEntity<>(response, responseHeaders, HttpStatus.EXPECTATION_FAILED);
                    }
                } else {
                    responseHeaders.set("status", "SELLER CAN ONLY UPDATE ORDERS FOR HIS/HER OWN PRODUCTS:(");
                    response.put("status", "FORBIDDEN");
                    response.put("description", "Seller doesn't own the product.");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.FORBIDDEN);
                }
            }
        }

        responseHeaders.set("status", "SELLER IS NOT AUTHORIZED TO UPDATE ORDERS:(");
        response.put("status", "Seller not authorized");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Controller method for updating a product order item for the <code>rating</code> field,
     * accessible for customer only.
     * @param id_token string with id_token for authentication, here must match with customer userUID
     * @return HTTP response entity with rated product order entry
     */
    @PutMapping("/customer/order/{orderId}")
    public ResponseEntity<Map<String, Object>> rateOrder(
            @PathVariable Long orderId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token,
            @RequestParam(value = "rating", defaultValue = "N/A") double rating) {
        Map<String, Object> response = new LinkedHashMap<>();
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();

        for (User user : users) {
            if (user.getUserUID().equals(uid)) {
                ProductOrder productOrder = productOrderService.getOrderById(orderId);
                if (productOrder.getCustomer().getUserUID().equals(uid)) {
                    if (productOrder != null) {
                        if (productOrder.getRating() == 0.0 && productOrder.isConfirmed()) {
                            productOrder.setRating(rating);
                            User seller = productOrder.getProduct().getUser();
                            if (seller.getRating().equals("N/A")) {
                                seller.setRating(String.format("%.1f", rating));
                            } else {
                                seller.setRating(String.format("%.1f",
                                        (Double.valueOf(seller.getRating()) + rating) / 2));
                            }
                            userService.update(seller);
                            responseHeaders.set("status", "PRODUCT ORDER SUCCESSFULLY RATED:)");
                            response.put("status", "OK");
                            response.put("product_order", productOrderService.save(productOrder));
                            return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                        } else if (productOrder.isConfirmed()) {
                            responseHeaders.set("status", "Order already rated by the customer:(");
                            response.put("status", "FORBIDDEN");
                            response.put("description", "Customer cannot rate the order for multiple times.");
                            return new ResponseEntity<>(response, responseHeaders, HttpStatus.FORBIDDEN);
                        } else {
                            responseHeaders.set("status", "ORDER NOT CONFIRMED:(");
                            response.put("status", "FORBIDDEN");
                            response.put("description", "Customer cannot rate unconfirmed orders.");
                            return new ResponseEntity<>(response, responseHeaders, HttpStatus.FORBIDDEN);
                        }
                    } else {
                        responseHeaders.set("status", "Unexpected Error:(");
                        response.put("status", "ERROR");
                        response.put("description", "Unexpected Error");
                        return new ResponseEntity<>(response, responseHeaders, HttpStatus.EXPECTATION_FAILED);
                    }
                } else {
                    responseHeaders.set("status", "ORDER DOESN'T BELONG TO USER:(");
                    response.put("status", "FORBIDDEN");
                    response.put("description", "Customers can only rate their own orders.");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.FORBIDDEN);
                }
            }
        }

        responseHeaders.set("status", "CUSTOMER IS NOT AUTHORIZED TO RATE ORDERS:(");
        response.put("status", "Customer not authorized");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Controller method for deleting a product order item, accessible for seller only.
     * @param id_token string with id_token for authentication, here must match with seller userUID
     * @return HTTP response entity with deleted product order entry
     */
    @DeleteMapping("/seller/order/{orderId}")
    public ResponseEntity<Map<String, Object>> deleteOrder(
            @PathVariable Long orderId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        Map<String, Object> response = new LinkedHashMap<>();
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();

        for (User user : users) {
            if (user.getUserUID().equals(uid)) {
                ProductOrder productOrder = productOrderService.getOrderById(orderId);
                if (productOrder.getProduct().getUser().getUserUID().equals(uid)) {
                    productOrderService.delete(productOrder);
                    responseHeaders.set("status", "OK");
                    response.put("status", "OK");
                    response.put("deleted_order", true);
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                } else {
                    responseHeaders.set("status", "SELLER CAN ONLY DELETE ORDERS FOR HIS/HER OWN PRODUCTS:(");
                    response.put("status", "FORBIDDEN");
                    response.put("description", "Seller doesn't own the product.");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.FORBIDDEN);
                }
            }
        }

        responseHeaders.set("status", "SELLER IS NOT AUTHORIZED TO DELETE ORDERS:(");
        response.put("status", "Seller not authorized");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Helper method to sort a LinkedHashMap by values.
     * @param linkedHashMap target LinkedHashMap
     * @param comparator a comparator instance for Integer values
     * @return a LinkedHashMap sorted by the values
     */
    private LinkedHashMap sortByValue(LinkedHashMap<ProductOrder, Integer> linkedHashMap, Comparator<Integer> comparator) {
        List<Map.Entry<ProductOrder, Integer>> entries = new ArrayList<>(linkedHashMap.entrySet());
        LinkedHashMap<ProductOrder, Integer> sorted = new LinkedHashMap<>();
        entries.stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, comparator))
                .forEachOrdered(entry -> sorted.put(entry.getKey(), entry.getValue()));

        return sorted;
    }

    private String verifyToken(String id_token) {
        FirebaseToken decodedToken = null;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(id_token);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        return decodedToken.getUid();
    }
}
