package com.github.jmgoyesc.ebtapi.e2e;

import com.fasterxml.jackson.databind.JsonNode;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Juan Manuel Goyes Coral
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class E2eTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @LocalServerPort
    private int port;

    @DisplayName("e2e-1. GIVEN mongo and app running, WHEN invoke create question, THEN data in mongo is present")
    @Test
    void test1() {
        //given
        var json = """
                {
                    "id": 30,
                    "text": "aaaa",
                    "options": [
                        { "index": 1, "text": "aa" },
                        { "index": 2, "text": "bb" },
                        { "index": 3, "text": "cc" },
                        { "index": 4, "text": "dd" }
                    ],
                    "answer": 1
                }
                """;

        //when
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        var response = restTemplate.postForEntity("http://localhost:%d/v1/admin/questions".formatted(port), request, String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        var questions = mongoTemplate.findAll(Document.class, "question");
        assertThat(questions.size()).isEqualTo(1);
        assertThat(questions.getFirst().getInteger("_id")).isEqualTo(30);
    }

    @DisplayName("e2e-2. GIVEN mongo and app running, WHEN invoke stats, THEN check 1 question stateful")
    @Test
    void test2() {
        //given

        //when
        var response = restTemplate.getForEntity("http://localhost:%d/v1/admin/questions/stats".formatted(port), JsonNode.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var questions = mongoTemplate.findAll(Document.class, "question");
        assertThat(questions.size()).isEqualTo(1);
        assertThat(questions.getFirst().getInteger("_id")).isEqualTo(30);
    }

}
