package com.williams.assessment.repository;

import com.williams.assessment.model.entity.SubjectRecord;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRecordRepository extends PagingAndSortingRepository<SubjectRecord, Long>, QueryDslPredicateExecutor<SubjectRecord> {

    List<SubjectRecord> findAll();
    SubjectRecord findOneByStudentKey(String studentKey);
    SubjectRecord findOneByUniqueKey(String uniqueKey);
}
