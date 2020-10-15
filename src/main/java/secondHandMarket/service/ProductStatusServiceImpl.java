/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import secondHandMarket.model.ProductStatus;
import secondHandMarket.repository.ProductStatusRepository;

@Service
@Transactional
public class ProductStatusServiceImpl implements ProductStatusService {
    ProductStatusRepository productStatusRepository;

    public ProductStatusServiceImpl(ProductStatusRepository productStatusRepository) {
        this.productStatusRepository = productStatusRepository;
    }


    @Override
    public Iterable<ProductStatus> getAllPostStatus() {
        return productStatusRepository.findAll();
    }

    @Override
    public ProductStatus getPostStatusByAvailability(boolean available) {
        Iterable<ProductStatus> postStatuses = productStatusRepository.findAll();
        for (ProductStatus productStatus : postStatuses) {
            if (productStatus.isAvailable() == available) {
                return productStatus;
            }
        }
        return null;
    }

    @Override
    public ProductStatus save(boolean available) {
        ProductStatus productStatus = productStatusRepository.save(new ProductStatus(available));
        return productStatus;
    }
}
