package com.github.jmgoyesc.ebtapi.domain.services;

import com.github.jmgoyesc.ebtapi.adapters.mongodb.AnswerRepository;
import com.github.jmgoyesc.ebtapi.adapters.mongodb.QuestionRepository;
import com.github.jmgoyesc.ebtapi.domain.models.Answer;
import com.github.jmgoyesc.ebtapi.domain.models.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Juan Manuel Goyes Coral
 */

@Service
@RequiredArgsConstructor
@Transactional
public class CreateService {

    private final ValidatorService validator;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public void execute(Question question, Answer answer) {
        validator.validate(question, answer);
        questionRepository.save(question);
        answerRepository.save(answer);
    }

}
