package com.github.jmgoyesc.ebtapi.adapters.web;

import com.github.jmgoyesc.ebtapi.domain.models.PatchQuestion;
import com.github.jmgoyesc.ebtapi.domain.models.Question;
import com.github.jmgoyesc.ebtapi.domain.models.QuestionFilter;
import com.github.jmgoyesc.ebtapi.domain.models.Stats;
import com.github.jmgoyesc.ebtapi.domain.services.CreateService;
import com.github.jmgoyesc.ebtapi.domain.services.DeleteService;
import com.github.jmgoyesc.ebtapi.domain.services.FilterService;
import com.github.jmgoyesc.ebtapi.domain.services.PatchService;
import com.github.jmgoyesc.ebtapi.domain.services.StatsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Juan Manuel Goyes Coral
 */

@RestController
@RequestMapping("/v1/admin/questions")
@RequiredArgsConstructor
public class AdminApi {

    private final CreateService creator;
    private final DeleteService deleter;
    private final FilterService filter;
    private final PatchService patcher;
    private final StatsService stats;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody @Valid CreateQuestionRequest request) {
        var question = request.toQuestion();
        var answer = request.toAnswer();
        creator.execute(question, answer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Valid @Positive int id) {
        deleter.execute(id);
    }

    @GetMapping
    public List<Question> filter(@RequestBody(required = false) QuestionFilter request) {
        return filter.execute(request);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable int id, @RequestBody @Valid PatchQuestion request) {
        patcher.execute(id, request);
    }

    @GetMapping("/stats")
    public Stats stats() {
        return stats.execute();
    }

}
