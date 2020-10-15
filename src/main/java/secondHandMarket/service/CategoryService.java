/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import org.springframework.validation.annotation.Validated;
import secondHandMarket.model.Category;

@Validated
public interface CategoryService {
    Iterable<Category> getAllCategories();
    Category getCategoryById(int id);
    Category save(Category category);
}
