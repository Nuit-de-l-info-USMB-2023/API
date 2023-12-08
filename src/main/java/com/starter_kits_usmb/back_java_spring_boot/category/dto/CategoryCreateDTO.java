package com.starter_kits_usmb.back_java_spring_boot.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryCreateDTO {

    @Size(max = 100)
    @NotBlank
    private String name;

}
