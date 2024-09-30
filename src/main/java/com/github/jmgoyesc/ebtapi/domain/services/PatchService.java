package com.github.jmgoyesc.ebtapi.domain.services;

import com.github.jmgoyesc.ebtapi.adapters.mongodb.AnswerRepository;
import com.github.jmgoyesc.ebtapi.adapters.mongodb.QuestionRepository;
import com.github.jmgoyesc.ebtapi.domain.models.Answer;
import com.github.jmgoyesc.ebtapi.domain.models.Option;
import com.github.jmgoyesc.ebtapi.domain.models.PatchQuestion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author Juan Manuel Goyes Coral
 */

@Service
@RequiredArgsConstructor
@Transactional
public class PatchService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ValidatorService validator;

    public void execute(int id, @Valid PatchQuestion request) {
        var question = questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("question by id does not exist"));
        var hasChanges = false;

        if (request.text() != null && !request.text().isBlank()) {
            hasChanges = true;
            question = question.withText(request.text());
        }
        if (request.options() != null && !request.options().isEmpty()) {
            hasChanges = true;
            request.options().forEach(it -> validator.validate(it, "option"));

            var options = new ArrayList<Option>();
            for (var option : question.options()) {
                var optionCombined = request.options().stream()
                        .filter(it -> it.index() == option.index())
                        .findAny()
                                .orElse(option);
                options.add(optionCombined);
            }
            question = question.withOptions(options);
        }
        if (hasChanges) {
            validator.validate(question);
            questionRepository.save(question);
        }

        if (request.answer() != null) {
            var answer = Answer.builder()
                    .question(question.id())
                    .option(request.answer())
                    .build();
            validator.validate(question, answer);
            answerRepository.save(answer);
        }
    }
}
