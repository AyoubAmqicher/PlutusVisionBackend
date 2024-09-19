package org.sid.plutusvision.dtos;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FinancialAdviceDTO {
    private Long id;
    private String title;
    private String contentPreview;
    private LocalDate updatedAt;
    private List<String> categories;

    // Constructor
    public FinancialAdviceDTO(Long id, String title, String content, LocalDate updatedAt, List<String> categories) {
        this.id = id;
        this.title = title;
        this.contentPreview = generateContentPreview(content);
        this.updatedAt = updatedAt;
        this.categories = categories;
    }

    // Method to generate a content preview
    private String generateContentPreview(String content) {
        if (content.length() > 100) {
            return content.substring(0, 100) + "...";
        } else {
            return content;
        }
    }
}
