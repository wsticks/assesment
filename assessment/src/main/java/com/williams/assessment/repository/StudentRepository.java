package com.williams.assessment.repository;

import com.williams.assessment.model.entity.Student;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student,Long>,QueryDslPredicateExecutor<Student> {
    Student findOneByUniquekey(String uniqueKey);
    Student findOneByFirstName (String firstName);
    List<Student> findAll();
}
