package com.williams.assessment.model.request;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.williams.assessment.model.constants.Status;
import com.williams.assessment.model.entity.QUser;
import lombok.*;
import org.springframework.data.querydsl.QSort;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class UserSearchRequest extends PaginatedSearchRequest{

    private final static String CREATED_AT = "createdAt";
    private final static String UPDATED_AT = "updatedAt";
    private final static String ASC = "asc";
    private final static String DESC = "desc";
    private Long toCreatedAt;
    private Long fromCreatedAt;
    private Long toUpdatedAt;
    private Long fromUpdatedAt;
    private String sortBy;
    private String vendorId;
    private String uniqueKey;
    private String firstName;
    private String lastName;
    private String email;
    private String roleId;
    private Long phone;
    private Status status;
    private String lastLoginDate;
    private String roleName;
    private String sortDirection;
    private String transactionId;
    private String fullText;
    private int pageNumber;
    private int pageSize;

    @Override
    public BooleanExpression getBooleanExpression() {
        QUser user = QUser.user;
        List<BooleanExpression> booleanExpressions = new ArrayList<>(2);
        matchUniqueKey(user, booleanExpressions);
        matchFirstName(user, booleanExpressions);
        matchlastName(user, booleanExpressions);
        matchEmail(user, booleanExpressions);
        matchRoleId(user, booleanExpressions);
        matchPhone(user, booleanExpressions);
        matchStatus(user, booleanExpressions);
        matchToCreatedAt(user, booleanExpressions);
        matchFromCreatedAt(user, booleanExpressions);
        matchToUpdatedAt(user, booleanExpressions);
        matchFromUpdatedAt(user, booleanExpressions);
        return mergeMatchers(booleanExpressions);
    }

    @Override
    protected QSort computeSort() {
        QUser user = QUser.user;
        QSort sort = new QSort(user.createdAt.desc());
        if (sortBy == null) {
            return sort;
        }
        if (sortBy.equals(UPDATED_AT)) {
            return sortByUpdatedAt(user);
        }
        if (sortBy.equals(CREATED_AT)) {
            return sortByCreatedAt(user);
        }
        return sort;
    }

    private QSort sortByUpdatedAt(QUser user) {
        if ((sortDirection != null) && sortDirection.toLowerCase().equals(ASC)) {
            return new QSort(user.updatedAt.asc());
        } else {
            return new QSort(user.updatedAt.desc());
        }
    }

    private QSort sortByCreatedAt(QUser user) {
        if ((sortDirection != null) && sortDirection.toLowerCase().equals(ASC)) {
            return new QSort(user.createdAt.asc());
        } else {
            return new QSort(user.createdAt.desc());
        }
    }

    private void matchUniqueKey(QUser user, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(user.uniqueKey.eq(uniqueKey));
        }
    }

    private void matchRoleId(QUser user, List<BooleanExpression> expressions) {
        if (roleId != null) {
            expressions.add(user.roleId.eq(roleId));
        }
    }

    private void matchStatus(QUser user, List<BooleanExpression> expressions) {
        if (status != null) {
            expressions.add(user.status.eq(status));
        }
    }

    private void matchPhone(QUser user, List<BooleanExpression> expressions) {
        if (phone != null) {
            expressions.add(user.phone.eq(String.valueOf(phone)));
        }
    }

    private void matchFirstName(QUser user, List<BooleanExpression> expressions) {
        if (firstName != null) {
            expressions.add(user.firstName.eq(firstName));
        }
    }

    private void matchlastName(QUser user, List<BooleanExpression> expressions) {
        if (lastName != null) {
            expressions.add(user.lastName.eq(lastName));
        }
    }

    private void matchEmail(QUser user, List<BooleanExpression> expressions) {
        if (email != null) {
            expressions.add(user.email.eq(email));
        }
    }

    private void matchToCreatedAt(QUser user, List<BooleanExpression> expressions) {
        if (toCreatedAt != null) {
            expressions.add(user.createdAt.before(new Timestamp(toCreatedAt)));
        }
    }

    private void matchFromCreatedAt(QUser user, List<BooleanExpression> expressions) {
        if (fromCreatedAt != null) {
            expressions.add(user.createdAt.after(new Timestamp(fromCreatedAt)));
        }
    }

    private void matchToUpdatedAt(QUser user, List<BooleanExpression> expressions) {
        if (toUpdatedAt != null) {
            expressions.add(user.updatedAt.before(new Timestamp(toUpdatedAt)));
        }
    }

    private void matchFromUpdatedAt(QUser user, List<BooleanExpression> expressions) {
        if (fromUpdatedAt != null) {
            expressions.add(user.updatedAt.after(new Timestamp(fromUpdatedAt)));
        }
    }
}
