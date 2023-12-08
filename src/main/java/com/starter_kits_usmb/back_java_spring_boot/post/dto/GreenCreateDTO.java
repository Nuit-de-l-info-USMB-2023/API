package com.starter_kits_usmb.back_java_spring_boot.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GreenCreateDTO {
    @NotBlank
    private String description;

    @NotNull
    private MultipartFile image;
}
