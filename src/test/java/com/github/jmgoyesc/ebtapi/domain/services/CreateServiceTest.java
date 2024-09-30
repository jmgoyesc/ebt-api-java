package com.github.jmgoyesc.ebtapi.domain.services;

import com.github.jmgoyesc.ebtapi.adapters.mongodb.AnswerRepository;
import com.github.jmgoyesc.ebtapi.adapters.mongodb.QuestionRepository;
import com.github.jmgoyesc.ebtapi.domain.models.Answer;
import com.github.jmgoyesc.ebtapi.domain.models.Question;
import com.github.jmgoyesc.ebtapi.domain.models.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.*;

/**
 * @author Juan Manuel Goyes Coral
 */

@DisplayName("UT. CreateServiceTest.")
@ExtendWith(MockitoExtension.class)
class CreateServiceTest {

    @InjectMocks
    private CreateService target;

    @Mock
    private ValidatorService validator;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private AnswerRepository answerRepository;

    private static final Question validQuestion = Question.builder()
            .id(1)
            .text("question number one")
            .options(List.of(
                    Option.builder().index(1).text("Option one").build(),
                    Option.builder().index(2).text("Option two").build(),
                    Option.builder().index(3).text("Option three").build(),
                    Option.builder().index(4).text("Option four").build()
            ))
            .build();
    private static final Answer validAnswer = Answer.builder()
            .question(1)
            .option(1)
            .build();

    @DisplayName("T1. GIVEN a question, WHEN invoke addQuestion, THEN call data.save")
    @Test
    void test1() {
        //given

        //when
        target.execute(validQuestion, validAnswer);

        //then
        then(validator).should().validate(validQuestion, validAnswer);
        then(questionRepository).should().save(validQuestion);
        then(answerRepository).should().save(validAnswer);
    }

}