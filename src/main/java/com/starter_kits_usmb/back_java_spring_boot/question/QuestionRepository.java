package com.starter_kits_usmb.back_java_spring_boot.question;

import com.starter_kits_usmb.back_java_spring_boot.exemple.Exemple;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
