/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import secondHandMarket.model.Product;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getAllProductsPaginated(int page, int postsPerPage);
    List<Product> getProductsByCategory(List<Product> products, String category);
    List<Product> getProductsByState(List<Product> products, String state);
    List<Product> getProductsByKeywords(List<Product> products, List<String> keywords);
    Product getProductById(long id);
    List<Product> getProductsByUserUID(String userUID);
    Product save(Product product);
    Product update(Product product);
    void delete(Product product);
}
