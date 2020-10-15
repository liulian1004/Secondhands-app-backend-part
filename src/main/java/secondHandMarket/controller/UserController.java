/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li, Shuyu Zhou
 * Email: helloimlixin@gmail.com, szhou12@u.rochester.edu
 */

package secondHandMarket.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import secondHandMarket.model.*;
import secondHandMarket.service.*;

import java.util.*;

/**
 * Controller class for users.
 *
 * @see <a href="https://www.javaguides.net/2018/11/spring-getmapping-postmapping-putmapping-deletemapping-
 * patchmapping.html">Spring Mappings for HTTP Methods</a>
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ProductService productService;
    private final FavoriteService favoriteService;
    private final ProductOrderService productOrderService;
    private final RequestService requestService;
    private List<Favorite> favorites;
    private List<User> users;
    private HttpHeaders responseHeaders;

    public UserController(UserService userService,
                          ProductService productService,
                          FavoriteService favoriteService,
                          RequestService requestService,
                          ProductOrderService productOrderService) {
        this.userService = userService;
        this.productService = productService;
        this.favoriteService = favoriteService;
        this.requestService = requestService;
        this.productOrderService = productOrderService;
        this.responseHeaders = new HttpHeaders();
    }

    /*******************************************************************************************************************
     ************************************         BASIC USER SECTION         *******************************************
     ******************************************************************************************************************/

    /**
     * Controller method for <code>HTTP GET</code> to fetch all the users in the database.
     * @param id_token <code>ID_TOKEN</code> for Firebase authentication
     * @return a <code>ResponseEntity</code> with fetched users upon request success
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        Map<String, Object> response = new LinkedHashMap<>();

        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);
                responseHeaders.set("status", "USER COLLECTION SUCCESSFULLY FETCHED:)");
                response.put("status", "OK");
                response.put("summary", String.format("%d users rendered.",
                        users.size()));
                response.put("users", users);
                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO PERFORM THIS ACTION:(");
        response.put("status", "UNAUTHORIZED");
        response.put("description", "The user is not authorized, try a different user or save a new user.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Controller method for <code>HTTP GET</code> to fetch single user item matching given ID_TOKEN.
     * @param id_token <code>ID_TOKEN</code> for Firebase authentication
     * @return a <code>ResponseEntity</code> for the fetched user item
     */
    @GetMapping(value = "/user")
    public ResponseEntity<Map<String, Object>> getUserById(
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        Map<String, Object> response = new LinkedHashMap<>();
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);
                if (userService.getUserById(uid) != null) {
                    responseHeaders.set("status", "USER ENTITY SUCCESSFULLY FETCHED:)");
                    response.put("status", "OK");
                    response.put("user", userService.getUserById(uid));
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                } else {
                    responseHeaders.set("status", "USER RESOURCE NOT FOUND:(");
                    response.put("status", "NOT FOUND");
                    response.put("description", "The user identity doesn't exist in the backend database");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.NOT_FOUND);
                }
            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO PERFORM THIS ACTION:(");
        response.put("status", "UNAUTHORIZED");
        response.put("description", "The user is not authorized, try a different user or save a new user.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Controller method for <code>HTTP POST</code> request, this mapping creates a new user identity in the backend
     * database if the user with the same userUID doesn't exist in the database, or update the user identity if user
     * already exists.
     * @param user request body of user item to update
     * @param id_token <code>ID_TOKEN</code> for Firebase authentication
     * @return a <code>ResponseEntity</code> containing the user created or updated
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> newUser(
            @RequestBody User user,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        User newUser = new User(uid);
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPhotoUrl(user.getPhotoUrl());
        newUser.setRating(user.getRating());
        User saveResponse = userService.save(newUser);
        responseHeaders = new HttpHeaders();
        Map<String, Object> response = new LinkedHashMap<>();
        // 409
        if (saveResponse != null) {
            responseHeaders.set("status", "USER ENTITY SUCCESSFULLY CREATED AND SAVED:)");
            response.put("status", "OK");
            response.put("user", saveResponse);
        } else {
            responseHeaders.set("status", "USER ENTITY ALREADY PRESENTS:(");
            response.put("status", "OK");
            User existingUser = userService.getUserById(uid);
            existingUser.setEmail(user.getEmail());
            existingUser.setPhotoUrl(user.getPhotoUrl());
            existingUser.setRating(user.getRating());
            existingUser.setName(user.getName());
            response.put("updated_user", userService.update(existingUser));
        }
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

    /**
     * Controller method for <code>HTTP DELETE</code> to delete a user item in the backend database.
     * @param id_token <code>ID_TOKEN</code> for Firebase authentication
     * @return a <code>ResponseEntity</code> indicating user deletion success
     */
    @DeleteMapping("/user")
    public ResponseEntity<Map<String, Object>> deleteUser(
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        responseHeaders = new HttpHeaders();
        users = userService.getAllUsers();
        Map<String, Object> response = new HashMap<>();
        if (userService.getUserById(uid) == null) {
            responseHeaders.set("status", "USER RESOURCE NOT FOUND:(");
            response.put("status", "NOT_FOUND");
            response.put("description", "The user entity doesn't exist in the database.");
            return new ResponseEntity<>(response, responseHeaders, HttpStatus.NOT_FOUND);
        }
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);
                User userById = userService.getUserById(uid);
                try {
                    userService.delete(userById);

                    response.put(String.format("User %s successfully deleted.", uid),
                            Boolean.TRUE);
                    responseHeaders.set("status", "USER ENTITY SUCCESSFULLY DELETED:)");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                } catch (Exception e) {
                    response.put(String.format("User %s successfully deleted.", uid),
                            Boolean.TRUE);
                    responseHeaders.set("status", "USER ENTITY SUCCESSFULLY DELETED:)");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                }
            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO PERFORM THIS ACTION:(");
        response.put("status", "UNAUTHORIZED");
        response.put("description", "The user is not authorized, try a different user or save a new user.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /*******************************************************************************************************************
     ************************************         USER PRODUCTS SECTION         ****************************************
     ******************************************************************************************************************/

    @GetMapping(value = "/user/products")
    public ResponseEntity<Map<String, Object>> getProductsByUserUID(
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        responseHeaders = new HttpHeaders();
        Map<String, Object> response = new LinkedHashMap<>();
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);
                List<Product> products = productService.getProductsByUserUID(uid);
                return getMapResponseEntity(response, products);
            }
        }

        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO VIEW PRODUCTS:(");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/user/products/{productId}")
    public ResponseEntity<Map<String, Object>> getProductByUserUID(
            @PathVariable Long productId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        responseHeaders = new HttpHeaders();
        Map<String, Object> response = new LinkedHashMap<>();
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);
                List<Product> products = productService.getProductsByUserUID(uid);
                for (Product product : products) {
                    if (product.getProductId().equals(productId)) {
                        responseHeaders.set("status", "PRODUCT OF USER IS FOUND:)");
                        response.put("status", "OK");
                        response.put("product of user", product);
                        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                    }
                }
                responseHeaders.set("status", "USER DOES NOT HAVE THE PRODUCT:(");

                return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
            }
        }

        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO VIEW PRODUCTS:(");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /*******************************************************************************************************************
     ************************************         USER FAVORITES SECTION         ***************************************
     ******************************************************************************************************************/

    @GetMapping("/user/favorites")
    public ResponseEntity<Map<String, Object>> getUserFavorites(
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        favorites = favoriteService.getFavorites();
        List<Favorite> userFavorites = new ArrayList<>();
        Map<String, Object> response = new LinkedHashMap<>();
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.println("User is authorized:)");
                if (user.getUserUID().equals(uid)) {
                    for (Favorite favorite: favorites) {
                        if (favorite.getUser().getUserUID().equals(uid)) {
                            favorite.getProduct().setFavorite(true);
                            userFavorites.add(favorite);
                        }
                    }

                    LinkedHashMap<Favorite, Integer> favoriteIntegerLinkedHashMap = new LinkedHashMap<>();

                    int timePenalty = 0;
                    int availabilityPenalty = userFavorites.size();
                    for (Favorite userFavorite: userFavorites) {
                        if (userFavorite.getProduct().isAvailability()) {
                            favoriteIntegerLinkedHashMap.put(userFavorite, 0 + timePenalty);
                        } else {
                            favoriteIntegerLinkedHashMap.put(userFavorite, availabilityPenalty + timePenalty);
                        }
                        timePenalty++;
                    }

                    favoriteIntegerLinkedHashMap = sortByValue(favoriteIntegerLinkedHashMap, Comparator.comparingInt(o -> o));

                    responseHeaders.set("status", "User favorites successfully rendered:)");
                    response.put("status", "OK");
                    response.put("num_of_favorites", userFavorites.size());
                    response.put("user_favorites", new ArrayList<>(favoriteIntegerLinkedHashMap.keySet()));

                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                } else {
                    responseHeaders.set(
                            "status", "USER IS NOT ALLOWED TO VIEW THE FAVORITES OF ANOTHER USER:(");

                    return new ResponseEntity<>(null, responseHeaders, HttpStatus.FORBIDDEN);
                }
            }
        }

        responseHeaders.set("status", "USER MUST REGISTER TO VIEW FAVORITES:(");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/user/favorites")
    public ResponseEntity<Map<String, Object>> addUserFavorite(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        Map<String, Object> response = new LinkedHashMap<>();
        if (productId.equals("N/A")) {
            responseHeaders.set("status", "INVALID PRODUCT ID:(");
            response.put("status", "failed");
            response.put("reason", "invalid product id");
            return new ResponseEntity<>(response, responseHeaders, HttpStatus.BAD_REQUEST);
        }
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.println("User is authorized:)");
                if (user.getUserUID().equals(uid)) {
                    Product product = productService.getProductById(productId);
                    Favorite favorite = new Favorite(product, user);
                    favoriteService.save(favorite);
                    responseHeaders.set("status", "User favorite successfully saved:)");
                    response.put("status", "OK");
                    favorite.getProduct().setFavorite(true);
                    response.put("saved_favorite_item", favorite);

                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                } else {
                    responseHeaders.set(
                            "status", "USER IS NOT ALLOWED TO SAVE THE FAVORITE FOR ANOTHER USER:(");

                    return new ResponseEntity<>(null, responseHeaders, HttpStatus.FORBIDDEN);
                }
            }
        }

        responseHeaders.set("status", "USER MUST REGISTER TO SAVE A FAVORITE ITEM:(");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/user/favorites")
    public ResponseEntity<Map<String, Object>> deleteUserFavorite(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        favorites = favoriteService.getFavorites();
        Map<String, Object> response = new LinkedHashMap<>();
        if (productId.equals("N/A")) {
            responseHeaders.set("status", "INVALID PRODUCT ID:(");
            response.put("status", "failed");
            response.put("reason", "invalid product id");
            return new ResponseEntity<>(response, responseHeaders, HttpStatus.BAD_REQUEST);
        }
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.println("User is authorized:)");
                if (user.getUserUID().equals(uid)) {
                    for (Favorite favorite: favorites) {
                        if (favorite.getProduct().getProductId().equals(productId)) {
                            favoriteService.delete(favorite);
                        }
                    }
                    responseHeaders.set("status", "User favorite successfully deleted:)");
                    response.put("status", "OK");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                } else {
                    responseHeaders.set(
                            "status", "USER IS NOT ALLOWED TO DELETE THE FAVORITE FOR ANOTHER USER:(");

                    return new ResponseEntity<>(null, responseHeaders, HttpStatus.FORBIDDEN);
                }
            }
        }

        responseHeaders.set("status", "USER MUST REGISTER TO DELETE A FAVORITE ITEM:(");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /*******************************************************************************************************************
     ******************************         CUSTOMER REQUEST SECTION         *******************************************
     ******************************************************************************************************************/

    /**
     * Get requests for the customer.
     * @param id_token <code>ID_TOKEN</code> to verify the customer identity
     * @return a <code>ResponseEntity</code> containing requests sent by the customer.
     */
    @GetMapping(value = "/customer/requests")
    public ResponseEntity<Map<String, Object>> getRequestsByCustomer(
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        responseHeaders = new HttpHeaders();
        Map<String, Object> response = new LinkedHashMap<>();

        List<Request> customerRequests = new ArrayList<>();

        for (User user:users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);

                List<Request> requests = requestService.getAllRequests();
                for (Request req:requests) {
                    if (req.getUser().getUserUID().equals(uid)) {
                        customerRequests.add(req);
                    }

                }
                responseHeaders.set("status", "OK");
                response.put("status", "OK");
                response.put("summary", String.format("%d requests found.", customerRequests.size()));
                response.put("requests", customerRequests);
                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO VIEW ORDERS:(");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /*
     * create request - customer
     */
    @PostMapping(value = "/customer/requests")
    public ResponseEntity<Map<String, Object>> createRequest(
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token,
            @RequestParam(value = "product_id", defaultValue = "N/A") Long productId) {
        String uid = verifyToken(id_token);
        List<User> users = userService.getAllUsers();
        responseHeaders = new HttpHeaders();
        Map<String, Object> response = new LinkedHashMap<>();

        for (User user:users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);

                Product product = productService.getProductById(productId);
                Request request = new Request(user, product);

                responseHeaders.set("status", "OK");
                response.put("status", "OK");
                response.put("saved_request", requestService.save(request));
                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO VIEW ORDERS:(");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /*
     * delete request - customer
     */
    @DeleteMapping("/customer/requests/request/{requestId}")
    public ResponseEntity<Map<String, Object>> deleteRequestByCustomer(
            @PathVariable Long requestId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        responseHeaders = new HttpHeaders();
        List<User> users = userService.getAllUsers();
        List<Request> requests = requestService.getAllRequests();

        if (requests.size() == 0) {
            responseHeaders.set("status", "NO REQUESTS TO UPDATE:(");
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.NO_CONTENT);
        }
        if (requestService.getRequestById(requestId) == null) {
            responseHeaders.set("status", "REQUEST RESOURCE NOT FOUND:(");
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
        }
        Map<String, Object> response = new LinkedHashMap<>();
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);

                System.out.println("DEBUG: User UID matched, proceed to perform the update operation.");

                Request request = requestService.getRequestById(requestId);


                responseHeaders.set("status", "REQUEST ENTITY SUCCESSFULLY UPDATED AND SAVED:)");
                response.put("status", "OK");
                requestService.delete(request);
                response.put("message", "Request canceled by the customer.");
                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);

            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO PERFORM THIS ACTION:(");
        response.put("status", "UNAUTHORIZED");
        response.put("description", "The user is not authorized, try a different user or save a new user.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /*******************************************************************************************************************
     ******************************         SELLER REQUEST SECTION         *********************************************
     ******************************************************************************************************************/

    /**
     * Get requests for products of the seller
     * @param id_token ID_TOKEN to fetch the seller userUID
     * @return ResponseEntity containing order requests for the products of the seller
     */
    @GetMapping(value = "/seller/requests")
    public ResponseEntity<Map<String, Object>> getOrderRequestsByUserUID(
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        List<User> users = userService.getAllUsers();
        responseHeaders = new HttpHeaders();
        Map<String, Object> response = new LinkedHashMap<>();

        List<Request> sellerRequests = new ArrayList<>();

        for (User user:users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);

                List<Request> requests = requestService.getAllRequests();
                for (Request req:requests) {
                    if (req.getProduct().getUser().getUserUID().equals(uid)) {
                        sellerRequests.add(req);
                    }

                }
                responseHeaders.set("status", "OK");
                response.put("status", "OK");
                response.put("summary", String.format("%d requests found.", sellerRequests.size()));
                response.put("requests", sellerRequests);
                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO VIEW ORDERS:(");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /*
     * update request - seller
     */
    @PutMapping("/seller/requests/request/{requestId}")
    public ResponseEntity<Map<String, Object>> updateOrderRequest(
            @PathVariable Long requestId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        responseHeaders = new HttpHeaders();
        Iterable<User> users = userService.getAllUsers();
        Iterable<Request> requests = requestService.getAllRequests();

        if (requests.spliterator().getExactSizeIfKnown() == 0) {
            responseHeaders.set("status", "NO REQUESTS TO UPDATE:(");
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.NO_CONTENT);
        }
        if (requestService.getRequestById(requestId) == null) {
            responseHeaders.set("status", "REQUEST RESOURCE NOT FOUND:(");
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
        }
        Map<String, Object> response = new LinkedHashMap<>();
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);

                System.out.println("DEBUG: User UID matched, proceed to perform the update operation.");

                Request request = requestService.getRequestById(requestId);
                request.setStatus("Confirmed");

                // Generate new Product Order.
                ProductOrder productOrder = new ProductOrder(request.getProduct(), request.getUser());
                productOrder.setConfirmed(true);

                responseHeaders.set("status", "REQUEST CONFIRMED AND ORDER CREATED:)");
                response.put("status", "OK");
                response.put("message", "request confirmed");
                response.put("created_order", productOrderService.save(productOrder));
                requestService.delete(request);

                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);

            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO PERFORM THIS ACTION:(");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /*
     * delete request - seller
     */
    @DeleteMapping("/seller/requests/request/{requestId}")
    public ResponseEntity<Map<String, Object>> deleteOrderRequest(
            @PathVariable Long requestId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        responseHeaders = new HttpHeaders();
        Iterable<User> users = userService.getAllUsers();
        Iterable<Request> requests = requestService.getAllRequests();

        if (requests.spliterator().getExactSizeIfKnown() == 0) {
            responseHeaders.set("status", "NO REQUESTS TO UPDATE:(");
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.NO_CONTENT);
        }
        if (requestService.getRequestById(requestId) == null) {
            responseHeaders.set("status", "REQUEST RESOURCE NOT FOUND:(");
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
        }
        Map<String, Object> response = new LinkedHashMap<>();
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);

                System.out.println("DEBUG: User UID matched, proceed to perform the update operation.");

                Request request = requestService.getRequestById(requestId);


                responseHeaders.set("status", "REQUEST ENTITY SUCCESSFULLY DELETED:)");
                response.put("status", "OK");
                requestService.delete(request);
                response.put("message", "Request declined by the seller.");
                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);

            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO PERFORM THIS ACTION:(");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /*******************************************************************************************************************
     **************************************         HELPER METHODS         *********************************************
     ******************************************************************************************************************/

    private ResponseEntity<Map<String, Object>> getMapResponseEntity(Map<String, Object> response,
                                                                     List<Product> products) {
        responseHeaders.set("status", "OK:)");
        responseHeaders.set("summary",
                String.format("%d products rendered.", products.size()));
        response.put("status", "OK");
        response.put("summary",
                String.format("%d products rendered.", products.size()));
        response.put("products_of_user", products);
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

    /**
     * Helper method to sort a LinkedHashMap by values.
     * @param linkedHashMap target LinkedHashMap
     * @param comparator a comparator instance for Integer values
     * @return a LinkedHashMap sorted by the values
     */
    private LinkedHashMap sortByValue(LinkedHashMap<Favorite, Integer> linkedHashMap, Comparator<Integer> comparator) {
        List<Map.Entry<Favorite, Integer>> entries = new ArrayList<>(linkedHashMap.entrySet());
        LinkedHashMap<Favorite, Integer> sorted = new LinkedHashMap<>();
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
