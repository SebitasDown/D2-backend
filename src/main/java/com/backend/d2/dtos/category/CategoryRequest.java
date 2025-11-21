package com.backend.d2.dtos.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "The category name is required")
    private String name;

    private String description;
}
