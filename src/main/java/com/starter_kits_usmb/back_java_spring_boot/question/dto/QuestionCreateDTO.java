package com.starter_kits_usmb.back_java_spring_boot.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionCreateDTO {

    @Size(max = 100)
    @NotBlank
    private String content;

}
