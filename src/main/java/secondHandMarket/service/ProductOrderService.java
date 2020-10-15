/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import secondHandMarket.model.ProductOrder;

import java.util.List;

public interface ProductOrderService {
    List<ProductOrder> getOrders();
    ProductOrder getOrderById(Long id);
    ProductOrder save(ProductOrder productOrder);
    void delete(ProductOrder productOrder);
}
