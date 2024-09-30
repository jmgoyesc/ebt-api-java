package com.github.jmgoyesc.ebtapi.domain.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Juan Manuel Goyes Coral
 */

@Document(collection = "answer")
@With @Builder
public record Answer(
        @Id @Positive int question,
        @Positive @Min(1) @Max(4) int option
) {}
