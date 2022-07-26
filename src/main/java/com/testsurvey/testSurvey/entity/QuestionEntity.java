package com.testsurvey.testSurvey.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "question")
public class QuestionEntity {

  @Id
  @Column(name = "id", nullable = false)
  private String questionId;
  private Boolean disabled;
  private String questionText;
  @NotNull
  @OneToMany(cascade = {CascadeType.ALL})
  private List<QuestionAnswerEntity> answer;

  public String getQuestionId() {
    return questionId;
  }

  public void setQuestionId(String questionId) {
    this.questionId = questionId;
  }

  public Boolean getDisabled() {
    return disabled;
  }

  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  public String getQuestionText() {
    return questionText;
  }

  public void setQuestionText(String questionText) {
    this.questionText = questionText;
  }

  public List<QuestionAnswerEntity> getAnswer(@NotNull @Valid List<QuestionAnswerEntity> answer) {
    return this.answer;
  }

  public void setAnswer(List<QuestionAnswerEntity> answer) {
    this.answer = answer;
  }
}
