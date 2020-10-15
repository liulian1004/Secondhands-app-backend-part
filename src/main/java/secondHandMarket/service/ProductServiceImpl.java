/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import secondHandMarket.exception.ResourceNotFoundException;
import secondHandMarket.model.Category;
import secondHandMarket.model.Product;
import secondHandMarket.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    @Override
    public List<Product> getAllProductsPaginated(int page, int pageSize) {
        Page<Product> paginated = productRepository.findAll(PageRequest.of(page, pageSize));
        return paginated.getContent();
    }

    @Override
    public List<Product> getProductsByCategory(List<Product> products, String category) {
        int categoryIdx = Category.getCategoryIdx(category);
        if (categoryIdx == 0) {
            return products;
        }
        List<Product> productsOfCategory = new ArrayList<>();
        for (Product product : products) {
            String productCategory = product.getCategory().toLowerCase();
            if (productCategory != null && productCategory.equals(category.toLowerCase())) {
                productsOfCategory.add(product);
            }
        }
        return productsOfCategory;
    }

    @Override
    public List<Product> getProductsByState(List<Product> products, String state) {
        if (state.equals("N/A")) {
            return products;
        }
        List<Product> productsByState = new ArrayList<>();
        for (Product product : products) {
            if (product.getState().equals(state)) {
                productsByState.add(product);
            }
        }
        return productsByState;
    }

    @Override
    public List<Product> getProductsByKeywords(List<Product> products, List<String> keywords) {
        if (keywords == null || keywords.size() == 0) {
            return products;
        }
        List<Product> productsByKeywords = new ArrayList<>();
        for (Product product : products) {
            StringBuffer postInfoBuffer = new StringBuffer(product.getProductName() + " ");
            String category = product.getCategory();
            postInfoBuffer.append(category.toLowerCase() + " ");
            postInfoBuffer.append(product.getDescription());
            String postInfo = postInfoBuffer.toString();
            if (containsAllKeywords(postInfo, keywords) && !productsByKeywords.contains(product)) {
                productsByKeywords.add(product);
            }
        }

        return productsByKeywords;
    }

    private boolean containsAllKeywords(String productInfo, List<String> keywords) {
        for (String keyword: keywords) {
            if (!productInfo.contains(keyword.toLowerCase())) return false;
        }

        return true;
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found:(")
        );
    }

    @Override
    public List<Product> getProductsByUserUID(String userUID) {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
        List<Product> productsByUserUID = new ArrayList<>();
        for (Product product : products) {
            if (product.getUser().getUserUID().equals(userUID)) {
                productsByUserUID.add(product);
            }
        }

        return productsByUserUID;
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product save(Product product) {
        Iterable<Product> existingProducts = productRepository.findAll();
        for (Product existingProduct : existingProducts) {
            if (existingProduct.getProductName().equals(product.getProductName())) {
                System.out.println("DEBUG: Product with the same name has already been created.");

                return null;
            }
        }
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
