package com.github.jmgoyesc.ebtapi.domain.services;

import com.github.jmgoyesc.ebtapi.adapters.mongodb.AnswerRepository;
import com.github.jmgoyesc.ebtapi.adapters.mongodb.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Juan Manuel Goyes Coral
 */

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public void execute(int id) {
        var exists = questionRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("Unknown question id");
        }
        questionRepository.deleteById(id);
        answerRepository.deleteById(id);
    }

}
