package com.starter_kits_usmb.back_java_spring_boot.answer.dto;

import com.starter_kits_usmb.back_java_spring_boot.question.Question;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AnswerCreateDTO {

    @Size(max = 100)
    @NotBlank
    private String content;

    @Size(max = 300)
    @NotBlank
    private String contentAnswer;

    @NotNull
    private Boolean isGoodAnswer;

    @NotNull
    private long question;

}
