package com.starter_kits_usmb.back_java_spring_boot.question.dao;

import com.starter_kits_usmb.back_java_spring_boot.category.Category;
import com.starter_kits_usmb.back_java_spring_boot.question.Question;
import lombok.Data;

import java.util.Collection;

@Data
public class QuestionDAO {
    private long id;
    private String content;
    //private Category category;
    private Collection<AnswerDAO> answers;

    public static QuestionDAO fromQuestion(Question question) {
        QuestionDAO questionDAO = new QuestionDAO();
        questionDAO.setId(question.getId());
        questionDAO.setContent(question.getContent());
        //questionDAO.setCategory(question.getCategory());
        return questionDAO;
    }
}
