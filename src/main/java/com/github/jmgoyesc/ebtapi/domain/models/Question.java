package com.github.jmgoyesc.ebtapi.domain.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Juan Manuel Goyes Coral
 */

@Document(collection = "question")
@With @Builder
public record Question(
        @Id @Positive int id,
        @NotBlank String text,
        @NotNull @Size(min = 4, max = 4) List<@Valid Option> options
) {

    public Set<Integer> optionIndexes() {
        return options().stream()
                .map(Option::index)
                .collect(Collectors.toSet());
    }

}
