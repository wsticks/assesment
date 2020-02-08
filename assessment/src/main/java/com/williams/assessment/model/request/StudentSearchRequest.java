package com.williams.assessment.model.request;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.williams.assessment.model.entity.QStudent;
import lombok.*;
import org.springframework.data.querydsl.QSort;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class StudentSearchRequest extends PaginatedSearchRequest {

    private final static String CREATED_AT = "createdAt";
    private final static String UPDATED_AT = "updatedAt";
    private final static String ASC = "asc";
    private final static String DESC = "desc";
    private Long toCreatedAt;
    private Long fromCreatedAt;
    private Long toUpdatedAt;
    private Long fromUpdatedAt;
    private String firstName;
    private String lastName;
    private String age;
    private String phone;
    private String email;
    private String sex;
    private String department;
    private String form;
    private String religion;
    private String address;
    private String nameOfNextOfKin;
    private String phoneNumberOfNextOfKin;
    private String uniqueKey;
    private String lastLoginDate;
    private String sortDirection;
    private String fullText;
    private int pageNumber;
    private int pageSize;
    private String sortBy;


    @Override
    public BooleanExpression getBooleanExpression() {
        QStudent student = QStudent.student;
        List<BooleanExpression> booleanExpressions = new ArrayList<>(2);
        matchUniqueKey(student,booleanExpressions);
        matchFirstName(student,booleanExpressions);
        matchLastName(student,booleanExpressions);
        matchAge(student,booleanExpressions);
        matchPhone(student,booleanExpressions);
        matchEmail(student,booleanExpressions);
        matchSex(student,booleanExpressions);
        matchDepartment(student,booleanExpressions);
        matchForm(student,booleanExpressions);
        matchReligion(student,booleanExpressions);
        matchAddress(student,booleanExpressions);
        matchNameOfNextOfKin(student,booleanExpressions);
        matchPhoneOfNextOfKin(student,booleanExpressions);
//        matchLastLoginDate(student,booleanExpressions);
        matchCreatedAt(student,booleanExpressions);
        matchUpdatedAt(student,booleanExpressions);
        return mergeMatchers(booleanExpressions);
    }



    @Override
    protected QSort computeSort() {
        QStudent student = QStudent.student;
        QSort sort = new QSort(student.createdAt.desc());
        if (sortBy == null) {
            return sort;
        }
        if (sortBy.equals(UPDATED_AT)) {
            return sortByUpdatedAt(student);
        }
        if(sortBy.equals(CREATED_AT)){
            return sortByCreatedAt(student);
        }
        return sort;
    }


    private QSort sortByUpdatedAt(QStudent student) {
        if ((sortDirection != null) && sortDirection.toLowerCase().equals(ASC)) {
            return new QSort(student.updatedAt.asc());
        } else {
            return new QSort(student.updatedAt.desc());
        }
    }

    private QSort sortByCreatedAt(QStudent student) {
        if ((sortDirection != null) && sortDirection.toLowerCase().equals(ASC)) {
            return new QSort(student.updatedAt.asc());
        } else {
            return new QSort(student.updatedAt.desc());
        }
    }

    private void matchUniqueKey(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.uniquekey.eq(uniqueKey));
        }
    }

    private void matchFirstName(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.firstName.eq(firstName));
        }
    }

  private void matchLastName(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.lastName.eq(lastName));
        }
    }

    private void matchAge(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.age.eq(age));
        }
    }

    private void matchPhone(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.phone.eq(phone));
        }
    }

    private void matchEmail(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.email.eq(email));
        }
    }

    private void matchSex(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.sex.eq(sex));
        }
    }

    private void matchDepartment(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.department.eq(department));
        }
    }

    private void matchForm(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.form.eq(form));
        }
    }

    private void matchReligion(QStudent student, List<BooleanExpression> expressions) {
            if (uniqueKey != null) {
                expressions.add(student.religion.eq(religion));
            }
    }

    private void matchAddress(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.address.eq(address));
        }
    }

    private void matchNameOfNextOfKin(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.nameOfNextOfKin.eq(nameOfNextOfKin));
        }
    }

    private void matchPhoneOfNextOfKin(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.phoneNumberOfNextOfKin.eq(phoneNumberOfNextOfKin));
        }
    }

    private void matchCreatedAt(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.createdAt.before(new Timestamp(toCreatedAt)));
        }
    }

    private void matchUpdatedAt(QStudent student, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(student.updatedAt.before(new Timestamp(toUpdatedAt)));
        }
    }
}
