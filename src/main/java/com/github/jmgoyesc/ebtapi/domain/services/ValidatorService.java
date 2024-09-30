package com.github.jmgoyesc.ebtapi.domain.services;

import com.github.jmgoyesc.ebtapi.domain.models.Answer;
import com.github.jmgoyesc.ebtapi.domain.models.Question;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author Juan Manuel Goyes Coral
 */

@Service
@RequiredArgsConstructor
public class ValidatorService {

    private final Validator validator;

    public void validate(Question question) {
        validate(question, "question");

        var options = question.optionIndexes();
        if (options.size() != 4) {
            throw new IllegalArgumentException("Invalid options. It must contains unique values from 1 to 4. Given: %s".formatted(options));
        }
    }

    public void validate(Question question, Answer answer) {
        validate(question);
        validate(answer, "answer");

        var options = question.optionIndexes();
        if (answer.question() != question.id()) {
            throw new IllegalArgumentException("Invalid question mapping to answer. Expected: %s, Given: %s".formatted(question.id(), answer.question()));
        }
        if (!options.contains(answer.option())) {
            throw new IllegalArgumentException("Invalid correct answer. The correct answer must be part of the options. Possible options: %s, Given: %s".formatted(options, answer.option()));
        }
    }

    void validate(Object toValidate, String name) {
        var violations = validator.validate(toValidate);
        if (!violations.isEmpty()) {
            var error = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Invalid %s format: %s".formatted(name, error));
        }
    }

}
