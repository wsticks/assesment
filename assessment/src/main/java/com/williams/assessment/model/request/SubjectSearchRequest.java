package com.williams.assessment.model.request;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.williams.assessment.model.entity.QStudent;
import com.williams.assessment.model.entity.QSubjects;
import lombok.*;
import org.springframework.data.querydsl.QSort;

import javax.enterprise.inject.New;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class SubjectSearchRequest extends  PaginatedSearchRequest {

    private final static String CREATED_AT = "createdAt";
    private final static String UPDATED_AT = "updatedAt";
    private final static String ASC = "asc";
    private final static String DESC = "desc";
    private Long toCreatedAt;
    private Long fromCreatedAt;
    private Long toUpdatedAt;
    private Long fromUpdatedAt;
    private String sortBy;
    private String uniqueKey;
    private String subjectName;
    private String subjectCode;
    private String subjectTeacherName;
    private String lastLoginDate;
    private String sortDirection;
    private String fullText;
    private int pageNumber;
    private int pageSize;


    @Override
    public BooleanExpression getBooleanExpression() {
        QSubjects subjects = QSubjects.subjects;
        List<BooleanExpression> booleanExpressions = new ArrayList<>(2);
        matchUniqueKey(subjects, booleanExpressions);
        matchSubjectName(subjects, booleanExpressions);
        matchSubjectCode(subjects, booleanExpressions);
        matchSubjectTeacherName(subjects, booleanExpressions);
        matchCreatedAt(subjects, booleanExpressions);
        matchUpdatedAt(subjects, booleanExpressions);
        matchToUpdatedAt(subjects, booleanExpressions);
        matchFromUpdatedAt(subjects, booleanExpressions);
        return mergeMatchers(booleanExpressions);
    }

    @Override
    protected QSort computeSort() {
        QSubjects subjects = QSubjects.subjects;
        QSort sort = new QSort(subjects.createdAt.desc());
        if (sortBy == null) {
            return sort;
        }
        if (sortBy.equals(UPDATED_AT)) {
            return sortByUpdatedAt(subjects);
        }
        if (sortBy.equals(CREATED_AT)) {
            return sortByCreatedAt(subjects);
        }
        return sort;
    }


    private QSort sortByUpdatedAt(QSubjects subjects) {
        if ((sortDirection != null) && sortDirection.toLowerCase().equals(ASC)) {
            return new QSort(subjects.updatedAt.asc());
        } else {
            return new QSort(subjects.updatedAt.desc());
        }
    }

    private QSort sortByCreatedAt(QSubjects subjects) {
        if ((sortDirection != null) && sortDirection.toLowerCase().equals(ASC)) {
            return new QSort(subjects.createdAt.asc());
        } else {
            return new QSort(subjects.createdAt.desc());
        }
    }

    private void matchUniqueKey(QSubjects subjects, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(subjects.uniqueKey.eq(uniqueKey));
        }
    }

    private void matchSubjectName(QSubjects subjects, List<BooleanExpression> expressions) {
        if (subjectName != null) {
            expressions.add(subjects.subjectName.eq(subjectName));
        }
    }

    private void matchSubjectCode(QSubjects subjects, List<BooleanExpression> expressions) {
        if (subjectCode != null) {
            expressions.add(subjects.subjectName.eq(subjectName));
        }
    }

    private void matchSubjectTeacherName(QSubjects subjects, List<BooleanExpression> expressions) {
        if (subjectTeacherName != null) {
            expressions.add(subjects.subjectTeacherName.eq(subjectTeacherName));
        }
    }

    private void matchCreatedAt(QSubjects subjects, List<BooleanExpression> expressions) {
        if (toCreatedAt != null) {
            expressions.add(subjects.createdAt.before(new Timestamp(toCreatedAt)));
        }
    }

    private void matchUpdatedAt(QSubjects subjects, List<BooleanExpression> expressions) {
        if (fromCreatedAt != null) {
            expressions.add(subjects.createdAt.after(new Timestamp(fromCreatedAt)));
        }
    }

    private void matchToUpdatedAt(QSubjects subjects, List<BooleanExpression> expressions) {
        if (toUpdatedAt != null) {
            expressions.add(subjects.updatedAt.before(new Timestamp(toUpdatedAt)));
        }
    }

    private void matchFromUpdatedAt(QSubjects subjects, List<BooleanExpression> expressions) {
        if (fromUpdatedAt != null) {
            expressions.add(subjects.updatedAt.after(new Timestamp(fromUpdatedAt)));
        }
    }
}
