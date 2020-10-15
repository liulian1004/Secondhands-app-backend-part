/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import secondHandMarket.model.Category;
import secondHandMarket.repository.CategoryRepository;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        Iterable<Category> categories = categoryRepository.findAll();
        for (Category category: categories) {
            if (category.getCategoryIdx() == id) {
                return category;
            }
        }
        System.out.println("DEBUG: Category index not defined.");
        return null;
    }

    @Override
    public Category save(Category category) {
        Iterable<Category> existingCategories = categoryRepository.findAll();
        for (Category existingCategory: existingCategories) {
            if (existingCategory.getCategoryName().equals(category.getCategoryName())) {
                System.out.println("DEBUG: Category already saved.");

                return null;
            }
        }
        return categoryRepository.save(category);
    }
}
