package com.williams.assessment.model.request;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.williams.assessment.model.entity.QSubjectRecord;
import lombok.*;
import org.springframework.data.querydsl.QSort;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public  class SubjectRecordSearchRequest extends PaginatedSearchRequest{

    private final static String CREATED_AT = "createdAt";
    private final static String UPDATED_AT = "updatedAt";
    private final static String ASC = "asc";
    private final static String DESC = "desc";
    private String toCreatedAt;
    private String fromCreatedAt;
    private String toUpdatedAt;
    private String  fromUpdatedAt;
    private String sortBy;
    private String subjectKey;
    private String studentKey;
    private String firstAssessmentTest;
    private String secondAssessmentTest;
    private String thirdAssessmentTest;
    private String fourthAssessmentTest;
    private String examinationScore;
    private String totalScore;
    private String uniqueKey;
    private String lastLoginDate;
    private String sortDirection;
    private String fullText;
    private int pageNumber;
    private int pageSize;


    @Override
    public BooleanExpression getBooleanExpression() {
        QSubjectRecord subjectRecord = QSubjectRecord.subjectRecord;
        List<BooleanExpression> booleanExpressions = new ArrayList<>(2);
        matchUniqueKey(subjectRecord, booleanExpressions);
        matchSubjectKey(subjectRecord, booleanExpressions);
        matchStudentKey(subjectRecord, booleanExpressions);
        matchFirstAssessmentTest(subjectRecord, booleanExpressions);
        matchSecondAssessmentTest(subjectRecord, booleanExpressions);
        matchThirdAssessmentTest(subjectRecord, booleanExpressions);
        matchFourthAssessmentTest(subjectRecord, booleanExpressions);
        matchExaminationScore(subjectRecord, booleanExpressions);
        matchTotalScore(subjectRecord, booleanExpressions);
        return mergeMatchers(booleanExpressions);
    }

    @Override
    protected QSort computeSort() {
        QSubjectRecord subjectRecord = QSubjectRecord.subjectRecord;
        QSort sort = new QSort(subjectRecord.createdAt.desc());
        if (sortBy == null){
            return sort;
        }
        if (sortBy.equals(UPDATED_AT)) {
            return sortByUpdatedAt(subjectRecord);
        }
        if (sortBy.equals(CREATED_AT)) {
            return sortByCreatedAt(subjectRecord);
        }
        return sort;
    }

    private  QSort sortByUpdatedAt(QSubjectRecord subjectRecord) {
        if ((sortDirection != null) && sortDirection.toLowerCase().equals(ASC)) {
            return new QSort(subjectRecord.updatedAt.asc());
        } else {
            return new QSort(subjectRecord.updatedAt.desc());
        }
    }

    private QSort sortByCreatedAt(QSubjectRecord subjectRecord) {
        if ((sortDirection != null) && sortDirection.toLowerCase().equals(ASC)) {
            return new QSort(subjectRecord.createdAt.asc());
        } else {
            return new QSort(subjectRecord.createdAt.desc());
        }
    }

    private void matchUniqueKey(QSubjectRecord subjectRecord, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(subjectRecord.uniqueKey.eq(uniqueKey));
        }
    }

    private void matchSubjectKey(QSubjectRecord subjectRecord, List<BooleanExpression> expressions) {
        if (subjectKey != null) {
            expressions.add(subjectRecord.subjectKey.eq(subjectKey));
        }
    }

    private void matchStudentKey(QSubjectRecord subjectRecord, List<BooleanExpression> expressions) {
        if (studentKey != null) {
            expressions.add(subjectRecord.studentKey.eq(studentKey));
        }
    }

    private void matchFirstAssessmentTest(QSubjectRecord subjectRecord, List<BooleanExpression> expressions) {
        if (firstAssessmentTest != null) {
            expressions.add(subjectRecord.firstAssessmentTest.eq(firstAssessmentTest));
        }
    }

    private void matchSecondAssessmentTest(QSubjectRecord subjectRecord, List<BooleanExpression> expressions) {
        if (secondAssessmentTest != null) {
            expressions.add(subjectRecord.secondAssessmentTest.eq(secondAssessmentTest));
        }
    }

    private void matchThirdAssessmentTest(QSubjectRecord subjectRecord, List<BooleanExpression> expressions) {
        if (thirdAssessmentTest != null) {
            expressions.add(subjectRecord.thirdAssessmentTest.eq(thirdAssessmentTest));
        }
    }

    private void matchFourthAssessmentTest(QSubjectRecord subjectRecord, List<BooleanExpression> expressions) {
        if (fourthAssessmentTest != null) {
            expressions.add(subjectRecord.fourthAssessmentTest.eq(fourthAssessmentTest));
        }
    }

    private void matchExaminationScore(QSubjectRecord subjectRecord, List<BooleanExpression> expressions) {
        if (examinationScore != null) {
            expressions.add(subjectRecord.examinationScore.eq(examinationScore));
        }
    }

    private void matchTotalScore(QSubjectRecord subjectRecord, List<BooleanExpression> expressions) {
        if (totalScore != null) {
            expressions.add(subjectRecord.totalScore.eq(Integer.valueOf(totalScore)));
        }
    }
}
