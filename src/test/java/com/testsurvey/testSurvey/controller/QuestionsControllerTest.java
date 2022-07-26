package com.testsurvey.testSurvey.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.testsurvey.testSurvey.api.QuestionsApiController;
import com.testsurvey.testSurvey.entity.QuestionAnswerEntity;
import com.testsurvey.testSurvey.entity.QuestionEntity;
import com.testsurvey.testSurvey.repository.QuestionRepository;
import com.testsurvey.testSurvey.repository.QuestionResponseRepository;
import com.testsurvey.testSurvey.util.MockDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = {QuestionsApiController.class, QuestionsController.class})
class QuestionsControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private QuestionRepository questionRepository;

  @MockBean
  private QuestionResponseRepository questionResponseRepository;

  @Test
  void should_return_ok_for_success_question() throws Exception {
    String validQuestion = MockDataProvider.getValidQuestion();
    String response = "Question q1 Successfully inserted";
    mockMvc.perform(post("/questions")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(validQuestion))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(response)));
  }

  @Test
  void should_disable_a_question() throws Exception {
    String validQuestion = MockDataProvider.getValidQuestionDisable();
    String response = "Question q1 Successfully Updated with true";
    QuestionEntity questionEntity = new QuestionEntity();
    Mockito.when(questionRepository.findByQuestionId("q1")).thenReturn(questionEntity);
    mockMvc.perform(put("/questions/{questionId}", "q1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(validQuestion))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(response)));
  }
}