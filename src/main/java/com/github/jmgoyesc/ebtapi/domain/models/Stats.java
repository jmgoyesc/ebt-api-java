package com.github.jmgoyesc.ebtapi.domain.models;

import lombok.Builder;
import lombok.With;

import java.util.List;

/**
 * @author Juan Manuel Goyes Coral
 */

@Builder
@With
public record Stats(
        int totalQuestions,
        int totalAnswers,
        List<Integer> missingQuestionIds,
        List<Integer> unknownQuestionAnswers,
        boolean correct
) {
}
