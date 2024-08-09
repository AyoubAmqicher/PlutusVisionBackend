package org.sid.plutusvision.repositories;

import org.sid.plutusvision.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
    List<Budget> findByUserId(Long userId);
    List<Budget> findByCategoryId(Long categoryId);
}
