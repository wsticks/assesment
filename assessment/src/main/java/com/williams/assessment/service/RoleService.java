package com.williams.assessment.service;

import com.williams.assessment.exception.AssessmentApiException;
import com.williams.assessment.exception.NotFoundException;
import com.williams.assessment.exception.ProcessingException;
import com.williams.assessment.model.constants.Status;
import com.williams.assessment.model.entity.Permission;
import com.williams.assessment.model.entity.Role;
import com.williams.assessment.model.entity.RolePermission;
import com.williams.assessment.model.entity.User;
import com.williams.assessment.model.request.UpsertRoleRequest;
import com.williams.assessment.repository.PermissionRepository;
import com.williams.assessment.repository.RolePermissionRepository;
import com.williams.assessment.repository.RoleRepository;
import com.williams.assessment.util.GatewayBeanUtil;
import com.williams.assessment.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    private static final String ROLE_NOT_FOUND = "Role not found";
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;


    @Autowired
    public RoleService(RoleRepository roleRepository,
                       PermissionRepository permissionRepository,
                       RolePermissionRepository rolePermissionRepository) {
        Assert.notNull(roleRepository);
        Assert.notNull(permissionRepository);
        Assert.notNull(rolePermissionRepository);
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public ArrayList getPermissionCodesForRole(Integer roleId) throws AssessmentApiException {
        List<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleId(roleId);
        List<Integer> permissionIds = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions) {
            permissionIds.add(rolePermission.getPermssionId());
        }
        ArrayList permissionCodes = new ArrayList<>();
        List<Permission> permissions = permissionRepository.findAllByIdIn(permissionIds);
        for (Permission permission : permissions) {
            permissionCodes.add(permission.getCode());
        }
        return permissionCodes;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public List<Role> getMerchantRoles() {
        return roleRepository.findAllByIsHidden(Status.INACTIVE);
    }

    public Role getRole(String uniqueKey) {
        return roleRepository.findOneByUniqueKey(uniqueKey);
    }

    public Role createRole(UpsertRoleRequest request, User user)
            throws AssessmentApiException {
        Role role = request.toRole(user.getUniqueKey());
        prepareRoleForCreation(role);
        role = roleRepository.save(role);
        addPermissions(request, role);
        return role;
    }

    public Role fetchByUniqueKey(String uniqueKey) {
        Role role = roleRepository.findOneByUniqueKey(uniqueKey);
        if (role == null) {
            throw new NotFoundException(ROLE_NOT_FOUND);
        }
        return role;
    }

    public Role updateRole(Role roleToUpdate, UpsertRoleRequest request,
                           User authenticatedUser) {
        rolePermissionRepository.deleteAllByRoleId(roleToUpdate.getId());
        GatewayBeanUtil
                .copyProperties(request.toRole(authenticatedUser.getUniqueKey()), roleToUpdate);
        addPermissions(request, roleToUpdate);
        return roleRepository.save(roleToUpdate);
    }

    private void addPermissions(UpsertRoleRequest request, Role role)
            throws AssessmentApiException {
        List<Integer> permissionIds = getValidPermissionIds(request.getPermissionIds());
        for (Integer permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermssionId(permissionId);
            rolePermission.setStatus(Status.ACTIVE);
            rolePermissionRepository.save(rolePermission);
        }
    }

    private List<Integer> getValidPermissionIds(List<Integer> ids) {
        List<Permission> validPermissions = permissionRepository.findAllByIdIn(ids);
        List<Integer> validIds = new ArrayList<>();
        for (Permission permission : validPermissions) {
            validIds.add(permission.getId());
        }
        return validIds;
    }

    private void prepareRoleForCreation(Role role) throws AssessmentApiException {
        generateUniqueKey(role);
    }

    private void generateUniqueKey(Role role) throws ProcessingException {
        if (role.getUniqueKey() != null) {
            return;
        }
        String rawKey = role.getName() + LocalDateTime.now() + Math.random();
        String uniqueKey = SecurityUtil.hashWithMd5(rawKey);
        role.setUniqueKey(uniqueKey);
    }

    public List<Integer> getRolePermissionIds(Integer roleId) {
        List<RolePermission> rolePermissions = rolePermissionRepository
                .findPermissionIdsByRoleId(roleId);
        List<Integer> permissionIds = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions) {
            permissionIds.add(rolePermission.getPermssionId());
        }
        List<Permission> permissions = permissionRepository.findAllByIdIn(permissionIds);
        return permissionIds;
    }

    public Role fetchRoleById(Integer roleId) {
        Role role = roleRepository.findOneById(roleId);
        if (role == null) {
            throw new NotFoundException(ROLE_NOT_FOUND);
        }
        return role;
    }
}
