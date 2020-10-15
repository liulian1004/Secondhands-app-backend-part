/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import secondHandMarket.exception.ResourceNotFoundException;
import secondHandMarket.model.ProductOrder;
import secondHandMarket.repository.ProductOrderRepository;

import java.util.List;

@Service
@Transactional
public class ProductOrderServiceImpl implements ProductOrderService {

    private ProductOrderRepository productOrderRepository;

    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Override
    public List<ProductOrder> getOrders() {
        return productOrderRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    @Override
    public ProductOrder getOrderById(Long id) {
        return productOrderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ProductOrder not found:(")
        );
    }

    @Override
    public ProductOrder save(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    @Override
    public void delete(ProductOrder productOrder) {
        productOrderRepository.delete(productOrder);
    }
}
