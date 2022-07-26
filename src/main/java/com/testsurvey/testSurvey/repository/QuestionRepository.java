package com.testsurvey.testSurvey.repository;

import com.testsurvey.testSurvey.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
  QuestionEntity findByQuestionId(String questionId);
}
