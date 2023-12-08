package com.starter_kits_usmb.back_java_spring_boot.answer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Collection<Answer> findAllByQuestionId(long QuestionId);
}
