package com.github.jmgoyesc.ebtapi.adapters.mongodb;

import com.github.jmgoyesc.ebtapi.domain.models.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Manuel Goyes Coral
 */

@Repository
public interface AnswerRepository extends MongoRepository<Answer, Integer> {
}
