package com.github.jmgoyesc.ebtapi.domain.services;

import com.github.jmgoyesc.ebtapi.adapters.mongodb.QuestionRepository;
import com.github.jmgoyesc.ebtapi.domain.models.Question;
import com.github.jmgoyesc.ebtapi.domain.models.QuestionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Juan Manuel Goyes Coral
 */

@Service
@RequiredArgsConstructor
public class FilterService {

    private final QuestionRepository questionRepository;

    public List<Question> execute(QuestionFilter filter) {
        if (filter == null || filter.ids() == null || filter.ids().isEmpty()) {
            return questionRepository.findAll();
        }

        return questionRepository.findAllById(filter.ids());
    }
}
