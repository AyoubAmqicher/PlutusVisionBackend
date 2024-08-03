package org.sid.plutusvision;

import org.sid.plutusvision.entities.Budget;
import org.sid.plutusvision.entities.Category;
import org.sid.plutusvision.entities.PasswordResetToken;
import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.enums.BudgetPeriod;
import org.sid.plutusvision.repositories.BudgetRepository;
import org.sid.plutusvision.repositories.CategoryRepository;
import org.sid.plutusvision.repositories.PasswordResetTokenRepository;
import org.sid.plutusvision.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class PlutusVisionApplication implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;


    public PlutusVisionApplication(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository, BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PlutusVisionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        User user1 = userRepository.findById(1L).orElse(null);
//        Category category1 = categoryRepository.findById(1L).orElse(null);
//        Category category2 = categoryRepository.findById(2L).orElse(null);
//        Category category3 = categoryRepository.findById(3L).orElse(null);
//
//        Budget budget1 = new Budget();
//        budget1.setName("Groceries");
//        budget1.setTotalAmount(500.00);
//        budget1.setAllocatedAmount(50.00);
//        budget1.setPeriod(BudgetPeriod.MONTH);
//        budget1.setUser(user1);
//        budget1.setCategory(category1);
//
//        Budget budget2 = new Budget();
//        budget2.setName("Rent");
//        budget2.setTotalAmount(1200.00);
//        budget2.setAllocatedAmount(1200.00);
//        budget2.setPeriod(BudgetPeriod.MONTH);
//        budget2.setUser(user1);
//        budget2.setCategory(category2);
//
//        Budget budget3 = new Budget();
//        budget3.setName("Utilities");
//        budget3.setTotalAmount(300.00);
//        budget3.setAllocatedAmount(150.00);
//        budget3.setPeriod(BudgetPeriod.MONTH);
//        budget3.setUser(user1);
//        budget3.setCategory(category3);
//
//        budgetRepository.saveAll(List.of(budget1, budget2, budget3));

    }
}
