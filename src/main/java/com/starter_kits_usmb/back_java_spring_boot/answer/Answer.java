package com.starter_kits_usmb.back_java_spring_boot.answer;

import com.starter_kits_usmb.back_java_spring_boot.category.Category;
import com.starter_kits_usmb.back_java_spring_boot.question.Question;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "answers")
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content")
    @NotBlank
    @Size(max = 100)
    private String content;

    @Column(name = "content_answer")
    @NotBlank
    @Size(max = 300)
    private String contentAnswer;

    @Column(name = "is_good_answer")
    @NotNull
    private Boolean isGoodAnswer;

    @ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    private Question question;
}
