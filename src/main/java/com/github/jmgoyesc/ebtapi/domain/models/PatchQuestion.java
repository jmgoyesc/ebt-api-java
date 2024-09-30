package com.github.jmgoyesc.ebtapi.domain.models;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.With;

import java.util.List;

/**
 * @author Juan Manuel Goyes Coral
 */

@Builder @With
public record PatchQuestion(
        String text,
        List<@Valid Option> options,
        Integer answer
) {
}
