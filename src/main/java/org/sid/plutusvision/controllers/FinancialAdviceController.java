package org.sid.plutusvision.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.sid.plutusvision.dtos.FinancialAdviceDTO;
import org.sid.plutusvision.services.FinancialAdviceService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/advices")
@RolesAllowed("CLIENT")
public class FinancialAdviceController {

    private final FinancialAdviceService financialAdviceService;

    public FinancialAdviceController(FinancialAdviceService financialAdviceService) {
        this.financialAdviceService = financialAdviceService;
    }

    @GetMapping
    public List<FinancialAdviceDTO> getAllFinancialAdvices() {
        return financialAdviceService.getAllFinancialAdvice();
    }
}
