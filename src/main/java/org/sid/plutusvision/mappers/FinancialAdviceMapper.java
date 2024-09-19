package org.sid.plutusvision.mappers;

import org.sid.plutusvision.dtos.FinancialAdviceDTO;
import org.sid.plutusvision.entities.Category;
import org.sid.plutusvision.entities.FinancialAdvice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialAdviceMapper {
    public List<FinancialAdviceDTO> convertToDTOs(List<FinancialAdvice> advices) {
        return advices.stream().map(advice -> {
            List<String> categoryNames = advice.getCategories().stream()
                    .map(Category::getName)
                    .collect(Collectors.toList());
            return new FinancialAdviceDTO(
                    advice.getId(),
                    advice.getTitle(),
                    advice.getContent(),
                    advice.getUpdatedAt(),
                    categoryNames
            );
        }).collect(Collectors.toList());
    }

}
