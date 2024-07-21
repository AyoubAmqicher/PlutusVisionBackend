package org.sid.plutusvision.repositories;

import org.sid.plutusvision.entities.Category;
import org.sid.plutusvision.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(TransactionType type);
}