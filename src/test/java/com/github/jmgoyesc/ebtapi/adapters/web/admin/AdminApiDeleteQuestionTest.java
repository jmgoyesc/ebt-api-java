package com.github.jmgoyesc.ebtapi.adapters.web.admin;

import com.github.jmgoyesc.ebtapi.adapters.web.AdminApi;
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

import java.util.stream.Stream;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * @author Juan Manuel Goyes Coral
 */

@DisplayName("UT. AdminApiDeleteQuestionTest.")
@ExtendWith(MockitoExtension.class)
@WebMvcTest(AdminApi.class)
class AdminApiDeleteQuestionTest {

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

    @DisplayName("T1. GIVEN a question id, WHEN DELETE /v1/admin/questions/{id}, THEN return 204 and invoke service")
    @Test
    void test1() throws Exception {
        //given
        var id = 1;

        //when
        mockMvc.perform(delete("/v1/admin/questions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        //then
        then(deleter).should().execute(id);
        then(creator).shouldHaveNoInteractions();
        then(filter).shouldHaveNoInteractions();
        then(patcher).shouldHaveNoInteractions();
        then(stats).shouldHaveNoInteractions();
    }

    @DisplayName("T2. GIVEN nothing in id, WHEN DELETE /v1/admin/questions/{id}, THEN return 404")
    @Test
    void test2() throws Exception {
        //given
        Object id = null;

        //when
        mockMvc.perform(delete("/v1/admin/questions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        //then
        then(deleter).shouldHaveNoInteractions();
        then(creator).shouldHaveNoInteractions();
        then(filter).shouldHaveNoInteractions();
        then(patcher).shouldHaveNoInteractions();
        then(stats).shouldHaveNoInteractions();
    }

    @SuppressWarnings("unused")
    @DisplayName("T3. WHEN DELETE /v1/admin/questions/{id}")
    @ParameterizedTest(name = "{displayName}. {index}. {0}")
    @MethodSource
    void test3(String name, Object id) throws Exception {
        //given

        //when
        mockMvc.perform(delete("/v1/admin/questions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        //then
        then(deleter).shouldHaveNoInteractions();
        then(creator).shouldHaveNoInteractions();
        then(filter).shouldHaveNoInteractions();
        then(patcher).shouldHaveNoInteractions();
        then(stats).shouldHaveNoInteractions();
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> test3() {
        return Stream.of(
                Arguments.of("negative id", -1),
                Arguments.of("non number", "a")
        );
    }


}