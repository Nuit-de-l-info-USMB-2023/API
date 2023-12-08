package com.starter_kits_usmb.back_java_spring_boot.question.dao;

import com.starter_kits_usmb.back_java_spring_boot.answer.Answer;
import com.starter_kits_usmb.back_java_spring_boot.question.Question;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AnswerDAO {
    private long id;
    private String content;
    private String contentAnswer;
    private Boolean isGoodAnswer;
    private long question;

    public static AnswerDAO fromAnswer(Answer answer, Question question) {
        AnswerDAO answerDAO = new AnswerDAO();
        answerDAO.setId(answer.getId());
        answerDAO.setContent(answer.getContent());
        answerDAO.setContentAnswer(answer.getContentAnswer());
        answerDAO.setIsGoodAnswer(answer.getIsGoodAnswer());
        answerDAO.setQuestion(question.getId());
        return answerDAO;
    }

}
