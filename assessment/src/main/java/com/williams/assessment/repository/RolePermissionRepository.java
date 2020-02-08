package com.williams.assessment.repository;

import com.williams.assessment.model.entity.RolePermission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RolePermissionRepository extends PagingAndSortingRepository<RolePermission,Long>,QueryDslPredicateExecutor<RolePermission> {

    List<RolePermission> findAllByRoleId(String roleId);

    List<RolePermission> findAllByRoleId(Integer roleId);

    @Modifying
    @Transactional
    @Query("DELETE from RolePermission rp where rp.roleId=:roleId")
    void deleteAllByRoleId(@Param("roleId") Integer roleId);

    List<RolePermission> findPermissionIdsByRoleId(Integer roleId);

}
