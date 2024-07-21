package org.sid.plutusvision.services;

import org.sid.plutusvision.entities.Category;
import org.sid.plutusvision.enums.TransactionType;

import java.util.List;

public interface CategoryService {
    List<Category> getCategoriesByType(TransactionType type);
}
