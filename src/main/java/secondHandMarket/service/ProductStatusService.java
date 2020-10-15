/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import secondHandMarket.model.ProductStatus;

public interface ProductStatusService {
    Iterable<ProductStatus> getAllPostStatus();
    ProductStatus getPostStatusByAvailability(boolean available);
    ProductStatus save(boolean available);
}
