package org.sid.plutusvision.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.sid.plutusvision.entities.Category;
import org.sid.plutusvision.enums.TransactionType;
import org.sid.plutusvision.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/categories")
@RolesAllowed("CLIENT")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Category>> getCategoriesByType(@PathVariable TransactionType type) {
        List<Category> categories = categoryService.getCategoriesByType(type);
        return ResponseEntity.ok(categories);
    }
}
