package com.starter_kits_usmb.back_java_spring_boot.question;

import com.starter_kits_usmb.back_java_spring_boot.category.Category;
import com.starter_kits_usmb.back_java_spring_boot.category.CategoryRepository;
import com.starter_kits_usmb.back_java_spring_boot.question.dto.QuestionCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Tag(name = "Question controller", description = "Endpoints for managing question")
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all questions")
    public List<Question> getAllQuestions() { return questionRepository.findAll(); }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get question by id")
    public Optional<Question> getQuestionById(@PathVariable long id) { return questionRepository.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get an question by id")
    public Question createQuestion(@Valid @RequestBody QuestionCreateDTO questionTDO) {
        Question question = new Question();
        question.setContent(questionTDO.getContent());
        Category category = categoryRepository.findById(questionTDO.getCategory()).orElseThrow();
        question.setCategory(category);
        return questionRepository.save(question);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update the question")
    public Optional<Question> updateQuestion(@Valid @RequestBody Question question) {
        if (questionRepository.existsById(question.getId())) {
            return Optional.of(questionRepository.save(question));
        }
        return Optional.empty();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a question")
    public void deleteQuestionById(@PathVariable long id) { questionRepository.deleteById(id);}

}
