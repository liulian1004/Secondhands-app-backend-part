/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import secondHandMarket.model.Product;
import secondHandMarket.service.ProductService;
import secondHandMarket.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/index")
public class HomePageController {

    private ProductService productService;
    private HttpHeaders responseHeaders;
    private int limit = 10;
    private int numOfCategories = 7;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Iterable<Product>> productsByCategory = new LinkedHashMap<>();
        List<Product> products = productService.getAllProducts();
        responseHeaders = new HttpHeaders();
        responseHeaders.set("status", "OK");
        response.put("status", "OK");
        // Append products by different categories.
        for (int i = 1; i <= numOfCategories; i++) {
            String category = Category.getCategoryNameByIdx(i);
            List<Product> productsByCurrentCategory = productService.getProductsByCategory(products, category);

            productsByCategory.put(processCategory(category), productsByCurrentCategory.size() >= limit ?
                    productsByCurrentCategory.subList(0, limit) : productsByCurrentCategory);
        }
        response.put("display_limit", String.format("%d products per category", limit));
        response.put("products_by_category", productsByCategory);

        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

    private String processCategory(String category) {
        return category.toLowerCase().replaceAll(" ", "_").replaceAll("&", "and");
    }
}
