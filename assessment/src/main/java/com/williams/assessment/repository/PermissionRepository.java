package com.williams.assessment.repository;

import com.williams.assessment.model.constants.Status;
import com.williams.assessment.model.entity.Permission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long> {

    List<Permission> findAllByIdIn(List<Integer> ids);

    Permission findOneById(String id);

    List<Permission> findAllByStatus(Status status);

    List<String> findAllCodeByIdIn(List<Integer> ids);

    List<Permission> findAllByStatusAndIsHidden(Status status, Status isHidden);
}
