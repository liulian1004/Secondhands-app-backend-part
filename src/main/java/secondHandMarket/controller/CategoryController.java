/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import secondHandMarket.model.Category;
import secondHandMarket.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = {"", "/"})
    public Iterable<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(value = "/{category_id}")
    public Category getCategoryById(@PathVariable int category_id) {
        return categoryService.getCategoryById(category_id);
    }
}
