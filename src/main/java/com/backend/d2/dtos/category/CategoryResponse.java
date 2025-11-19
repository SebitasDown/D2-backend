package com.backend.d2.dtos.category;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private long productCount;
}
