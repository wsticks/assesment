package com.williams.assessment.repository;

import com.williams.assessment.model.entity.User;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>,QueryDslPredicateExecutor<User> {

    User findOneById(Integer id);

    User findOneByUniqueKey(String UniqueKey);

    User findOneByEmail(String email);

    User findOneByPhone(String phone);

    List<User> findAll();
}
