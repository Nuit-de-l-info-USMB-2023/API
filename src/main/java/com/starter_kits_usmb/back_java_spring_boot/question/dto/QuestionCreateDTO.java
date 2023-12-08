package com.starter_kits_usmb.back_java_spring_boot.question.dto;

import com.starter_kits_usmb.back_java_spring_boot.category.Category;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionCreateDTO {

    @Size(max = 100)
    @NotBlank
    private String content;

    @NotNull
    private long category;

}
