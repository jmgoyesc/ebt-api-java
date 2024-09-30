package com.github.jmgoyesc.ebtapi.domain.services;

import com.github.jmgoyesc.ebtapi.adapters.mongodb.AnswerRepository;
import com.github.jmgoyesc.ebtapi.adapters.mongodb.QuestionRepository;
import com.github.jmgoyesc.ebtapi.domain.models.Answer;
import com.github.jmgoyesc.ebtapi.domain.models.Question;
import com.github.jmgoyesc.ebtapi.domain.models.Stats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Juan Manuel Goyes Coral
 */

@Service
@RequiredArgsConstructor
public class StatsService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Stats execute() {
        var questions = questionRepository.findAll();
        var answers = answerRepository.findAll();

        var questionIds = questions.stream().map(Question::id).toList();
        var answerQuestionIds = answers.stream().map(Answer::question).toList();

        //questions without answer. questionIds - answers.questionIds
        var missingQuestionIds = new ArrayList<>(questionIds);
        missingQuestionIds.removeAll(answerQuestionIds);

        //answers for questions does not exist
        var unknownQuestionAnswers = new ArrayList<>(answerQuestionIds);
        unknownQuestionAnswers.removeAll(questionIds);

        return Stats.builder()
                .totalQuestions(questions.size())
                .totalAnswers(answers.size())
                .missingQuestionIds(missingQuestionIds)
                .unknownQuestionAnswers(unknownQuestionAnswers)
                .correct(missingQuestionIds.isEmpty() && unknownQuestionAnswers.isEmpty())
                .build();
    }
}
