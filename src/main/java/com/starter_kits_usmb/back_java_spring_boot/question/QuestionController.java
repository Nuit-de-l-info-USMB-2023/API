package com.starter_kits_usmb.back_java_spring_boot.question;

import com.starter_kits_usmb.back_java_spring_boot.answer.Answer;
import com.starter_kits_usmb.back_java_spring_boot.answer.AnswerRepository;
import com.starter_kits_usmb.back_java_spring_boot.answer.dao.AnswerDAO;
import com.starter_kits_usmb.back_java_spring_boot.category.Category;
import com.starter_kits_usmb.back_java_spring_boot.category.CategoryRepository;
import com.starter_kits_usmb.back_java_spring_boot.question.dao.QuestionDAO;
import com.starter_kits_usmb.back_java_spring_boot.question.dto.QuestionCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Tag(name = "Question controller", description = "Endpoints for managing question")
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final AnswerRepository answerRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all questions")
    public Collection<QuestionDAO> getAllQuestions() {
        Collection<Question> questions = questionRepository.findAll();
        Collection<QuestionDAO> questionsDAO = new ArrayList<>();
        for (Question question : questions) {
            QuestionDAO questionDAO = QuestionDAO.fromQuestion(question);
            Collection<Answer> answers = answerRepository.findAllByQuestionId(question.getId());
            ArrayList<AnswerDAO> answersDAO = new ArrayList<>();
            for (Answer answer:answers) {
                AnswerDAO answerDAO = AnswerDAO.fromAnswer(answer);
                answersDAO.add(answerDAO);
            }
            questionDAO.setAnswers(answersDAO);
            questionsDAO.add(questionDAO);

        }
        return questionsDAO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get question by id")
    public Optional<QuestionDAO> getQuestionById(@PathVariable long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()) {
            return Optional.empty();
        }
        QuestionDAO questionDAO = QuestionDAO.fromQuestion(question.get());
        Collection<Answer> answers = answerRepository.findAllByQuestionId(question.get().getId());
        ArrayList<AnswerDAO> answersDAO = new ArrayList<>();
        for (Answer answer:answers) {
            AnswerDAO answerDAO = AnswerDAO.fromAnswer(answer);
            answersDAO.add(answerDAO);
        }
        questionDAO.setAnswers(answersDAO);
        return Optional.of(questionDAO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get an question by id")
    public QuestionDAO createQuestion(@Valid @RequestBody QuestionCreateDTO questionTDO) {
        Question question = new Question();
        question.setContent(questionTDO.getContent());
        Category category = categoryRepository.findById(questionTDO.getCategory()).orElseThrow();
        question.setCategory(category);
        Question questionCreated = questionRepository.save(question);
        QuestionDAO questionDAO = QuestionDAO.fromQuestion(questionCreated);
        Collection<Answer> answers = answerRepository.findAllByQuestionId(questionCreated.getId());
        ArrayList<AnswerDAO> answersDAO = new ArrayList<>();
        for (Answer answer:answers) {
            AnswerDAO answerDAO = AnswerDAO.fromAnswer(answer);
            answersDAO.add(answerDAO);
        }
        questionDAO.setAnswers(answersDAO);
        return QuestionDAO.fromQuestion(questionCreated);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update the question")
    public Optional<QuestionDAO> updateQuestion(@Valid @RequestBody Question question) {
        if (questionRepository.existsById(question.getId())) {
            Question questionUpdated = questionRepository.save(question);
            QuestionDAO questionDAO = QuestionDAO.fromQuestion(questionUpdated);
            Collection<Answer> answers = answerRepository.findAllByQuestionId(questionUpdated.getId());
            ArrayList<AnswerDAO> answersDAO = new ArrayList<>();
            for (Answer answer:answers) {
                AnswerDAO answerDAO = AnswerDAO.fromAnswer(answer);
                answersDAO.add(answerDAO);
            }
            questionDAO.setAnswers(answersDAO);
            return Optional.of(QuestionDAO.fromQuestion(questionUpdated));
        }
        return Optional.empty();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a question")
    public void deleteQuestionById(@PathVariable long id) { questionRepository.deleteById(id);}

}
