package com.starter_kits_usmb.back_java_spring_boot.answer;

import com.starter_kits_usmb.back_java_spring_boot.answer.dto.AnswerCreateDTO;
import com.starter_kits_usmb.back_java_spring_boot.category.Category;
import com.starter_kits_usmb.back_java_spring_boot.question.Question;
import com.starter_kits_usmb.back_java_spring_boot.question.QuestionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
@Tag(name = "Answer controller", description = "Endpoints for managing answer")
public class AnswerController {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all answers")
    public List<Answer> getAllAnswers() { return answerRepository.findAll(); }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get answer by id")
    public Optional<Answer> getAnswerById(@PathVariable long id) { return answerRepository.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get an answer by id")
    public Answer createAnswer(@Valid @RequestBody AnswerCreateDTO answerCreateDTO) {
        Answer answer = new Answer();
        answer.setContent(answerCreateDTO.getContent());
        answer.setContentAnswer(answerCreateDTO.getContentAnswer());
        answer.setIsGoodAnswer(answerCreateDTO.getIsGoodAnswer());
        Question question = questionRepository.findById(answerCreateDTO.getQuestion()).orElseThrow();
        answer.setQuestion(question);
        return answerRepository.save(answer);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update the question")
    public Optional<Answer> updateQuestion(@Valid @RequestBody Answer answer) {
        if (answerRepository.existsById(answer.getId())) {
            return Optional.of(answerRepository.save(answer));
        }
        return Optional.empty();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a question")
    public void deleteQuestionById(@PathVariable long id) { answerRepository.deleteById(id);}

}
