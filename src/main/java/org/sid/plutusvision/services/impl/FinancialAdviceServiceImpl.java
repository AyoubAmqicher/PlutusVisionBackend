package org.sid.plutusvision.services.impl;

import org.sid.plutusvision.dtos.FinancialAdviceDTO;
import org.sid.plutusvision.mappers.FinancialAdviceMapper;
import org.sid.plutusvision.repositories.FinancialAdviceRepository;
import org.sid.plutusvision.services.FinancialAdviceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialAdviceServiceImpl implements FinancialAdviceService {
    private final FinancialAdviceRepository financialAdviceRepository;
    private final FinancialAdviceMapper financialAdviceMapper;

    public FinancialAdviceServiceImpl(FinancialAdviceRepository financialAdviceRepository, FinancialAdviceMapper financialAdviceMapper) {
        this.financialAdviceRepository = financialAdviceRepository;
        this.financialAdviceMapper = financialAdviceMapper;
    }

    @Override
    public List<FinancialAdviceDTO> getAllFinancialAdvice() {
        return financialAdviceMapper.convertToDTOs(financialAdviceRepository.findAll());
    }
}
