package com.github.jmgoyesc.ebtapi.domain.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.With;

/**
 * @author Juan Manuel Goyes Coral
 */

@With @Builder
public record Option(
        @Positive @Min(1) @Max(4) int index,
        @NotBlank String text
) { }
