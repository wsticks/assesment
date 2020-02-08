package com.williams.assessment.model.request;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;

import java.util.Iterator;
import java.util.List;

@Data
 abstract class PaginatedSearchRequest {

    private static final int DEFAULT_SIZE = 10;
    protected int pageNumber;
    protected int pageSize;

    public Pageable getPaginationQuery() {
        pageNumber = (this.getPageNumber() > 0) ? this.getPageNumber() : 0;
        pageSize = (this.getPageSize() > 0) ? this.getPageSize() : DEFAULT_SIZE;
        return new QPageRequest(pageNumber, pageSize, computeSort());
    }

    BooleanExpression mergeMatchers(List<BooleanExpression> booleanExpressions) {
        BooleanExpression booleanExpression = null;
        Iterator<BooleanExpression> iterator = booleanExpressions.iterator();
        while (iterator.hasNext()) {
            if (booleanExpression == null) {
                booleanExpression = iterator.next();
            } else {
                booleanExpression = booleanExpression.and(iterator.next());
            }
        }
        return booleanExpression;
    }

    protected abstract BooleanExpression getBooleanExpression();

    protected abstract QSort computeSort();
}
