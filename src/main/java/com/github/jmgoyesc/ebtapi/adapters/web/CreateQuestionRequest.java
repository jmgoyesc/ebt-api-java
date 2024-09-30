package com.github.jmgoyesc.ebtapi.adapters.web;

import com.github.jmgoyesc.ebtapi.domain.models.Answer;
import com.github.jmgoyesc.ebtapi.domain.models.Option;
import com.github.jmgoyesc.ebtapi.domain.models.Question;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.With;

import java.util.List;

/**
 * @author Juan Manuel Goyes Coral
 */

@With @Builder
public record CreateQuestionRequest(
        @Positive int id,
        @NotBlank String text,
        @NotNull @Size(min = 4, max = 4) List<@Valid Option> options,
        @Positive @Min(1) @Max(4) int answer
) {

    public Question toQuestion() {
        return Question.builder()
                .id(id())
                .text(text())
                .options(options())
                .build();
    }

    public Answer toAnswer() {
        return Answer.builder()
                .question(id())
                .option(answer())
                .build();
    }

}
