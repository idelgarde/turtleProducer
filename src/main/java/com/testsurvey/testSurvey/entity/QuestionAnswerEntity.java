package com.testsurvey.testSurvey.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class QuestionAnswerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="id_sequence")
  @SequenceGenerator(name = "id_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
  private Long id;

  private String questionId;
  private String responseText;
  private Boolean isCorrect;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getResponseText() {
    return responseText;
  }

  public void setResponseText(String responseText) {
    this.responseText = responseText;
  }

  public Boolean getCorrect() {
    return isCorrect;
  }

  public void setCorrect(Boolean correct) {
    isCorrect = correct;
  }

  public String getQuestionId() {
    return questionId;
  }

  public void setQuestionId(String questionId) {
    this.questionId = questionId;
  }
}
