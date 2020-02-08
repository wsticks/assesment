package com.williams.assessment.repository;

import com.williams.assessment.model.entity.Token;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends PagingAndSortingRepository<Token, Long>{

    Token findOneByToken(String token);

    Token findOneByUserAndToken(String userKey, String token);
}
