package org.sid.plutusvision.services.impl;

import org.sid.plutusvision.entities.Category;
import org.sid.plutusvision.enums.TransactionType;
import org.sid.plutusvision.repositories.CategoryRepository;
import org.sid.plutusvision.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategoriesByType(TransactionType type) {
        return categoryRepository.findByType(type);
    }

}
