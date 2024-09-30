package com.github.jmgoyesc.ebtapi.adapters.mongodb;

import com.github.jmgoyesc.ebtapi.domain.models.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Manuel Goyes Coral
 */

@Repository
public interface QuestionRepository extends MongoRepository<Question, Integer> {
}
