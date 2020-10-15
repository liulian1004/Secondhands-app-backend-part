/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import secondHandMarket.exception.ResourceNotFoundException;
import secondHandMarket.model.Favorite;
import secondHandMarket.model.Product;
import secondHandMarket.model.User;
import secondHandMarket.service.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Controller class for products.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private UserService userService;
    private ProductService productService;
    private CategoryService categoryService;
    private ProductStatusService productStatusService;
    private FavoriteService favoriteService;
    private List<Favorite> favorites;
    private List<User> users;
    private List<Product> products;
    private HttpHeaders responseHeaders;

    public ProductController(UserService userService,
                             ProductService productService,
                             CategoryService categoryService,
                             ProductStatusService productStatusService,
                             FavoriteService favoriteService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.productStatusService = productStatusService;
        this.favoriteService = favoriteService;
        this.responseHeaders = new HttpHeaders();
    }

    /**
     * <code>HTTP GET</code> method to retrieve all the product posting for registered users.
     * @param page page number for pagination, default 0
     * @param page_size page size for pagination, default 10
     * @param id_token <code>ID_TOKEN</code> for Firebase authentication
     * @return  a <code>ResponseEntity</code> containing all the product items retrieved upon request success.
     */
    @GetMapping(value = {"", "/"})
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "page_size", defaultValue = "10") int page_size,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        Map<String, Object> response = new LinkedHashMap<>();
        // Load favorite items.
        favorites = favoriteService.getFavorites();
        for (User user : users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);
                List<Product> products = productService.getAllProducts();
                List<Product> productsOfPage = productService.getAllProductsPaginated(page - 1, page_size);
                responseHeaders.set("status", "Full product list fetched successfully:)");
                response.put("status", "OK");
                response.put("summary", String.format("%d products found", products.size()));
                response.put("page_size", page_size);
                response.put("page", page);
                // Update favorite field.
                for (Product product: productsOfPage) {
                    product.setFavorite(checkFavorite(user, product));
                }
                response.put("products", productsOfPage);

                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO VIEW POSTS:(");
        response.put("status", "User not authorized");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/nearby")
    public ResponseEntity<Map<String, Object>> getProductsNearby(
            @RequestParam(value = "lat", defaultValue = "37.38") String lat,
            @RequestParam(value = "lon", defaultValue = "-122.08") String lon,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        Map<String, Object> response = new LinkedHashMap<>();
        // Load favorite items.
        favorites = favoriteService.getFavorites();
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);
                double latitude = Double.valueOf(lat);
                double longitude = Double.valueOf(lon);
                double latPlus = latitude + 0.25;
                double latMinus = latitude - 0.25;
                double lonPlus = longitude + 0.25;
                double lonMinus = longitude - 0.25;


                List<Product> productsNearby = new ArrayList<>();
                LinkedHashMap<Product, Double> map = new LinkedHashMap<>();
                List<Product> products = productService.getAllProducts();

                for (Product product : products) {
                    if (product.isGPSEnabled() && product.isAvailability()) {
                        double productLat = product.getLat();
                        double productLon = product.getLon();
                        if (inRange(productLat, Math.min(latMinus, latPlus), Math.max(latMinus, latPlus))
                                && inRange(productLon, Math.min(lonMinus, lonPlus), Math.max(lonMinus, lonPlus))) {
                            double dist = Math.sqrt(
                                    Math.abs(productLat - latitude) + Math.abs(productLon - longitude));
                            map.put(product, dist);
                        }
                    }
                }

                LinkedHashMap<Product, Double> sortedMap = sortByValue(map, (o1, o2) -> (int) (o1 - o2));
                for (Map.Entry<Product, Double> entry: sortedMap.entrySet()) {
                    productsNearby.add(entry.getKey());
                }

                // Update favorite field.
                for (Product product: productsNearby) {
                    product.setFavorite(checkFavorite(user, product));
                }

                responseHeaders.set("status", "Nearby available products fetched successfully:)");
                response.put("status", "OK");
                response.put("coordinates", String.format("lat: %s, lon: %s", lat, lon));
                response.put("available_products_nearby",
                        productsNearby.size() >= 50 ? productsNearby.subList(0, 50) : productsNearby);

                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO VIEW POSTS:(");
        response.put("status", "User not authorized");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<Map<String, Object>> getProductById(
            @PathVariable Long productId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        Map<String, Object> response = new LinkedHashMap<>();
        // Load favorite items.
        favorites = favoriteService.getFavorites();
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);
                try {
                    responseHeaders.set("status", "OK");
                    response.put("status", "OK");
                    Product product = productService.getProductById(productId);
                    product.setFavorite(checkFavorite(user, product));
                    response.put("product", product);
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                } catch (ResourceNotFoundException e) {
                    responseHeaders.set("status", "POST RESOURCE NOT FOUND:(");
                    response.put("status", "NOT FOUND");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.NOT_FOUND);
                }
            }
        }
        responseHeaders.set("status", "USER AUTHORIZATION IS REQUIRED TO VIEW POSTS:(");
        response.put("status", "USER NOT AUTHORIZED");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Map<String, Object>> getProductsByFilter(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "page_size", defaultValue = "10") int page_size,
            @RequestParam(value = "keywords", required = false) List<String> keywords,
            @RequestParam(value = "category", defaultValue = "N/A") String category,
            @RequestParam(value = "state", defaultValue = "N/A") String state,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        HttpHeaders responseHeaders = new HttpHeaders();
        Map<String, Object> response = new LinkedHashMap<>();
        // Load favorite items.
        favorites = favoriteService.getFavorites();
        for (User user : users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);
                List<Product> products = productService.getAllProducts();
                List<Product> productsByKeywords = productService.getProductsByKeywords(products, keywords);
                List<Product> productsByCategory = productService.getProductsByCategory(productsByKeywords, category);
                List<Product> productsByState = productService.getProductsByState(productsByCategory, state);
                PageRequest pageRequest = PageRequest.of(page - 1, page_size);
                responseHeaders.set("status", "OK");
                response.put("status", "OK");
                response.put("summary", String.format("%d products found.", productsByState.size()));
                response.put("page", page);
                response.put("page_size", page_size);
                for (Product product: productsByState) {
                    product.setFavorite(checkFavorite(user, product));
                }
                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), productsByState.size());
                Page<Product> paginated = new PageImpl<>(
                        productsByState.subList(start, end), pageRequest, productsByState.size());
                response.put("products", paginated.getContent());
                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
            }
        }
        responseHeaders.set("status", "USER AUTHORIZATION IS REQUIRED TO VIEW POSTS:(");
        response.put("status", "USER NOT AUTHORIZED");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Controller method for <code>HTTP POST</code> request to create a new product item in the backend database
     * @param product request body containing product information
     * @param id_token <code>ID_TOKEN</code> for Firebase authentication
     * @return a <code>ResponseEntity</code> containing newly created product item.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> newProduct(
            @Valid @RequestBody Product product,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        users = userService.getAllUsers();
        Map<String, Object> response = new LinkedHashMap<>();
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                if (product.getUser().getUserUID().equals(uid)) {
                    System.out.println("DEBUG: User is authorized:)");
                    Product newProduct = new Product(user, product.getProductName(), product.getState());
                    newProduct.setPrice(product.getPrice());
                    newProduct.setDescription(product.getDescription());
                    String categoryName = product.getCategory();
                    newProduct.setCategory(categoryName);
                    newProduct.setImageUrls(product.getImageUrls());
                    newProduct.setLocation(product.getLat(), product.getLon(), product.getCity());
                    newProduct.setAvailability(product.isAvailability());
                    Product saveResponse = productService.save(newProduct);

                    if (saveResponse != null) {
                        responseHeaders.set("status", "PRODUCT ENTITY SUCCESSFULLY CREATED AND SAVED:)");
                        response.put("status", "OK");
                        response.put("product", saveResponse);
                        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                    } else {
                        responseHeaders.set("status", "PRODUCT ENTITY ALREADY PRESENTS:(");
                        response.put("status", "CONFLICT");
                        response.put("description", "Product with the same name already exists in the database, " +
                                "consider using a new product name.");
                        return new ResponseEntity<>(response, responseHeaders, HttpStatus.CONFLICT);
                    }
                } else {
                    responseHeaders.set("status", "PRODUCT USER UID MUST MATCH WITH THE AUTHORIZED USER:(");
                    response.put("status", "FORBIDDEN");
                    response.put("description", "The userUID given does not match the seller of the product, " +
                            "modification is prohibited.");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.FORBIDDEN);
                }
            }
        }

        responseHeaders.set("status", "USER AUTHORIZATION IS REQUIRED TO CREATE POSTS:(");
        response.put("status", "USER NOT AUTHORIZED");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Map<String, Object>> updateProduct(
            @RequestBody Product updatedProduct,
            @PathVariable Long productId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        products = productService.getAllProducts();
        users = userService.getAllUsers();
        Map<String, Object> response = new HashMap<>();
        if (productService.getProductById(productId) == null) {
            responseHeaders.set("status", "PRODUCT RESOURCE NOT FOUND:(");
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
        }
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);
                if (productService.getProductById(productId).getUser().getUserUID().equals(uid)) {
                    System.out.println(
                            "DEBUG: User UID matched, proceed to perform the update operation.");
                    Product productById = productService.getProductById(productId);
                    String categoryName = updatedProduct.getCategory();
                    productById.setCategory(categoryName);
                    productById.setPrice(updatedProduct.getPrice());
                    productById.setDescription(updatedProduct.getDescription());
                    productById.setImageUrls(updatedProduct.getImageUrls());
                    productById.setProductName(updatedProduct.getProductName());
                    productById.setLocation(updatedProduct.getLat(),
                            updatedProduct.getLon(),
                            updatedProduct.getCity());
                    productById.setAvailability(updatedProduct.isAvailability());
                    productById.setFavorite(updatedProduct.isFavorite());

                    responseHeaders.set("status", "PRODUCT ENTITY SUCCESSFULLY UPDATED:)");
                    response.put(String.format("Product item %s successfully UPDATED", productById.getProductName()),
                            Boolean.TRUE);
                    response.put("updated_product", productService.update(productById));
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                } else {
                    responseHeaders.set(
                            "status", "USER IS NOT ALLOWED TO UPDATE THE PRODUCT OF ANOTHER USER:(");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.FORBIDDEN);
                }
            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO PERFORM THIS ACTION:(");
        response.put("status", "USER NOT AUTHORIZED");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(
            @PathVariable Long productId,
            @RequestParam(value = "id_token", defaultValue = "N/A") String id_token) {
        String uid = verifyToken(id_token);
        responseHeaders = new HttpHeaders();
        products = productService.getAllProducts();
        users = userService.getAllUsers();
        Map<String, Object> response = new HashMap<>();
        if (productService.getProductById(productId) == null) {
            responseHeaders.set("status", "POST RESOURCE NOT FOUND:(");
            response.put("status", "NOT FOUND");
            response.put("description", "Product not found.");
            return new ResponseEntity<>(response, responseHeaders, HttpStatus.NOT_FOUND);
        }
        for (User user: users) {
            if (user.getUserUID().equals(uid)) {
                System.out.printf("DEBUG: User %s is authorized.\n", uid);
                if (productService.getProductById(productId).getUser().getUserUID().equals(uid)) {
                    System.out.println(
                            "DEBUG: User UID matched, proceed to perform the delete operation.");
                    Product productById = productService.getProductById(productId);
                    productService.delete(productById);

                    responseHeaders.set("status", "PRODUCT ENTITY SUCCESSFULLY DELETED:)");
                    response.put("status", "OK");
                    response.put("message", String.format("Product item %s successfully deleted.",
                            productById.getProductName()));

                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                } else {
                    responseHeaders.set(
                            "status", "USER IS NOT ALLOWED TO DELETE THE PRODUCT POSTING OF ANOTHER USER:(");
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.FORBIDDEN);
                }
            }
        }
        responseHeaders.set("status", "USER IS NOT AUTHORIZED TO PERFORM THIS ACTION:(");
        response.put("status", "USER NOT AUTHORIZED");
        response.put("description", "Please check if the userUID corresponds to the " +
                "given id_token exists in the backend database.");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Helper method to sort a LinkedHashMap by values.
     * @param linkedHashMap target LinkedHashMap
     * @param comparator a comparator instance for Double values
     * @return a LinkedHashMap sorted by the values
     */
    private LinkedHashMap sortByValue(LinkedHashMap<Product, Double> linkedHashMap, Comparator<Double> comparator) {
        List<Map.Entry<Product, Double>> entries = new ArrayList<>(linkedHashMap.entrySet());
        LinkedHashMap<Product, Double> sorted = new LinkedHashMap<>();
        entries.stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, comparator))
                .forEachOrdered(entry -> sorted.put(entry.getKey(), entry.getValue()));

        return sorted;
    }

    /**
     * Helper method deciding if a double is in a specific range.
     * @param num target double
     * @param low lower bound
     * @param high upper bound
     * @return boolean indicating the double is within the bounds specified
     */
    private boolean inRange(double num, double low, double high) {
        if (num >= low && num <= high)
            return true;

        return false;
    }

    /**
     * Helper Method to check the product is a favorite item of the given user.
     * @param user user of interest
     * @param product product of interest
     * @return boolean indicating whether product is a favorite item of the given user.
     */
    private boolean checkFavorite(User user, Product product) {
        for (Favorite favorite: favorites) {
            if (favorite.getProduct().getProductName().equals(product.getProductName())
                    && favorite.getUser().getUserUID().equals(user.getUserUID())) {
                return true;
            }
        }

        return false;
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
