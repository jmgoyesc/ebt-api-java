package com.github.jmgoyesc.ebtapi.adapters.web.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jmgoyesc.ebtapi.adapters.web.AdminApi;
import com.github.jmgoyesc.ebtapi.adapters.web.CreateQuestionRequest;
import com.github.jmgoyesc.ebtapi.domain.models.Answer;
import com.github.jmgoyesc.ebtapi.domain.models.Question;
import com.github.jmgoyesc.ebtapi.domain.models.Option;
import com.github.jmgoyesc.ebtapi.domain.services.CreateService;
import com.github.jmgoyesc.ebtapi.domain.services.DeleteService;
import com.github.jmgoyesc.ebtapi.domain.services.FilterService;
import com.github.jmgoyesc.ebtapi.domain.services.PatchService;
import com.github.jmgoyesc.ebtapi.domain.services.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * @author Juan Manuel Goyes Coral
 */

@DisplayName("UT. AdminApiTest - Post add question.")
@ExtendWith(MockitoExtension.class)
@WebMvcTest(AdminApi.class)
class AdminApiPostAddQuestionTest {

    @InjectMocks
    private AdminApi target;

    @MockBean
    private CreateService creator;
    @MockBean
    private DeleteService deleter;
    @MockBean
    private FilterService filter;
    @MockBean
    private PatchService patcher;
    @MockBean
    private StatsService stats;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private static final CreateQuestionRequest validRequest = CreateQuestionRequest.builder()
            .id(1)
            .text("question number one")
            .options(List.of(
                    Option.builder().index(1).text("Option one").build(),
                    Option.builder().index(2).text("Option two").build(),
                    Option.builder().index(3).text("Option three").build(),
                    Option.builder().index(4).text("Option four").build()
            ))
            .answer(1)
            .build();

    @DisplayName("T1. GIVEN a question, WHEN invoke POST /v1/admin/questions, THEN call AdminService and return 204")
    @Test
    void test1() throws Exception {
        //given
        var bytes = mapper.writeValueAsBytes(validRequest);

        //when
        mockMvc.perform(post("/v1/admin/questions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bytes))
                .andDo(print())
                .andExpect(status().isNoContent());

        //then
        then(creator).should().execute(any(Question.class), any(Answer.class));
        then(deleter).shouldHaveNoInteractions();
        then(filter).shouldHaveNoInteractions();
        then(patcher).shouldHaveNoInteractions();
        then(stats).shouldHaveNoInteractions();
    }

    @DisplayName("T2. GIVEN empty body, WHEN invoke POST /v1/admin/questions, THEN return 400")
    @Test
    void test2() throws Exception {
        //given

        //when
        mockMvc.perform(post("/v1/admin/questions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        //then
        then(creator).shouldHaveNoInteractions();
        then(deleter).shouldHaveNoInteractions();
        then(filter).shouldHaveNoInteractions();
        then(patcher).shouldHaveNoInteractions();
        then(stats).shouldHaveNoInteractions();
    }

    @DisplayName("T3. GIVEN invalid request, WHEN invoke POST /v1/admin/questions, THEN return 400")
    @ParameterizedTest(name = "{displayName}. {index}. {0}")
    @MethodSource
    void test3(String ignoredName, CreateQuestionRequest request) throws Exception {
        //given
        var bytes = mapper.writeValueAsBytes(request);

        //when
        mockMvc.perform(post("/v1/admin/questions")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(bytes)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        //then
        then(creator).shouldHaveNoInteractions();
        then(deleter).shouldHaveNoInteractions();
        then(filter).shouldHaveNoInteractions();
        then(patcher).shouldHaveNoInteractions();
        then(stats).shouldHaveNoInteractions();
    }

    private static Stream<Arguments> test3() {
        return Stream.of(
                Arguments.of("empty question", CreateQuestionRequest.builder().build()),
                Arguments.of("null text", validRequest.withText(null)),
                Arguments.of("negative id", validRequest.withId(-1)),
                Arguments.of("empty options", validRequest.withOptions(List.of())),
                Arguments.of("null options", validRequest.withOptions(null)),
                Arguments.of("correct answer 5", validRequest.withAnswer(5)),
                Arguments.of("correct answer negative", validRequest.withAnswer(-1)),
                Arguments.of("5 options", validRequest.withOptions(List.of(
                        Option.builder().index(1).text("1").build(),
                        Option.builder().index(2).text("2").build(),
                        Option.builder().index(3).text("3").build(),
                        Option.builder().index(4).text("4").build(),
                        Option.builder().index(5).text("5").build()
                ))),
                Arguments.of("3 options", validRequest.withOptions(List.of(
                        Option.builder().index(1).text("1").build(),
                        Option.builder().index(2).text("2").build(),
                        Option.builder().index(3).text("3").build()
                )))
        );
    }

}