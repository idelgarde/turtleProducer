package com.testsurvey.testSurvey.controller;

import com.testsurvey.testSurvey.api.QuestionsApiDelegate;
import com.testsurvey.testSurvey.api.model.EnableQuestion;
import com.testsurvey.testSurvey.api.model.Question;
import com.testsurvey.testSurvey.api.model.QuestionAnswer;
import com.testsurvey.testSurvey.entity.QuestionEntity;
import com.testsurvey.testSurvey.entity.QuestionAnswerEntity;
import com.testsurvey.testSurvey.repository.QuestionRepository;
import com.testsurvey.testSurvey.repository.QuestionResponseRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class QuestionsController implements QuestionsApiDelegate {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuestionResponseRepository questionResponseRepository;


    @Override
    @SneakyThrows
    public ResponseEntity<String> createQuestion(Question question) {
      List<QuestionAnswerEntity> questionAnswerEntityList = new ArrayList<>();
      for (QuestionAnswer questionsResponse : question.getAnswer()) {
          QuestionAnswerEntity questionAnswerEntity = new QuestionAnswerEntity();
          questionAnswerEntity.setResponseText(questionsResponse.getAnswerText());
          questionAnswerEntity.setCorrect(questionsResponse.getIsCorrect());
          questionAnswerEntity.setQuestionId(question.getQuestionId());
          questionAnswerEntityList.add(questionAnswerEntity);
        }

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setQuestionId(question.getQuestionId());
        questionEntity.setDisabled(question.getDisabled());
        questionEntity.setQuestionText(question.getQuestionText());
        questionEntity.setAnswer(questionAnswerEntityList);
        questionRepository.save(questionEntity);
      return new ResponseEntity<>(String.format("Question %s Successfully inserted",question.getQuestionId()), HttpStatus.OK);
    }

    @Override
    @SneakyThrows
    public ResponseEntity<String> enableQuestion(String questionId, EnableQuestion enableQuestion) {
      QuestionEntity questionEntity = questionRepository.findByQuestionId(questionId);
      questionEntity.setDisabled(enableQuestion.getDisabled());
      questionRepository.save(questionEntity);
      return new ResponseEntity<>(String.format("Question %s Successfully Updated with %s",enableQuestion.getQuestionId(),
          enableQuestion.getDisabled()), HttpStatus.OK);
    }

    @Override
    @SneakyThrows
    public ResponseEntity<Question> getQuestionById(String questionId) {
      List<QuestionAnswerEntity>  questionAnswerEntityList = questionResponseRepository.findAllByQuestionId(questionId);
      List<QuestionAnswer> questionResponseList = convertQuestionAnswerFromEntity(questionAnswerEntityList);
      QuestionEntity questionEntity = questionRepository.findByQuestionId(questionId);
      Question question =convertQuestionFromEntity(questionEntity, questionResponseList);
      return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Question>> getQuestionList() {
        List<QuestionEntity> questionEntityList = questionRepository.findAll();
        List<Question> questions = new ArrayList<>();
        for(QuestionEntity questionEntity: questionEntityList) {
          List<QuestionAnswerEntity>  questionAnswerEntityList = questionResponseRepository.findAllByQuestionId(questionEntity.getQuestionId());
          List<QuestionAnswer> questionResponseList = convertQuestionAnswerFromEntity(questionAnswerEntityList);
          Question question = convertQuestionFromEntity(questionEntity, questionResponseList);
          questions.add(question);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    private Question convertQuestionFromEntity(QuestionEntity questionEntity, List<QuestionAnswer> questionsAnswerList) {
      Question question = new Question();
      question.setQuestionId(questionEntity.getQuestionId());
      question.setDisabled(questionEntity.getDisabled());
      question.setQuestionText(questionEntity.getQuestionText());
      question.setAnswer(questionsAnswerList);
      return question;
    }

  private List<QuestionAnswer> convertQuestionAnswerFromEntity(List<QuestionAnswerEntity> questionAnswerEntityList) {
    List<QuestionAnswer> questionsAnswerList = new ArrayList<>();
    for (QuestionAnswerEntity questionAnswerEntity : questionAnswerEntityList) {
      QuestionAnswer questionsResponse = new QuestionAnswer();
      questionsResponse.setAnswerText(questionAnswerEntity.getResponseText());
      questionsResponse.setIsCorrect(questionAnswerEntity.getCorrect());
      questionsAnswerList.add(questionsResponse);
    }
    return questionsAnswerList;
  }
}
