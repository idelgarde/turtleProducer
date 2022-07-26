package com.testsurvey.testSurvey.repository;

import com.testsurvey.testSurvey.entity.QuestionAnswerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionResponseRepository extends JpaRepository<QuestionAnswerEntity, Long> {
  List<QuestionAnswerEntity> findAllByQuestionId(String questionId);
}
