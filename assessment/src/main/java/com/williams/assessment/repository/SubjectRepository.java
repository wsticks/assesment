package com.williams.assessment.repository;

import com.williams.assessment.model.entity.Subjects;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.security.auth.Subject;
import java.util.List;

@Repository
public interface SubjectRepository extends PagingAndSortingRepository<Subjects, Long>, QueryDslPredicateExecutor<Subjects>{

    Subjects findOneByUniqueKey(String uniqueKey);
    Subjects findOneBySubjectCode(String subjectCode);

}
