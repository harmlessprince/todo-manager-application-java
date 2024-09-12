package com.harmlessprince.todomanagerapplication.seeders;

import com.harmlessprince.todomanagerapplication.Repositories.PermissionRepository;
import com.harmlessprince.todomanagerapplication.Repositories.RoleRepository;
import com.harmlessprince.todomanagerapplication.models.Permission;
import com.harmlessprince.todomanagerapplication.models.Role;
import com.harmlessprince.todomanagerapplication.services.RoleService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RolePermissionSeeder {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    RoleService roleService;

    public RolePermissionSeeder(RoleRepository roleRepository, PermissionRepository permissionRepository, RoleService roleService) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.roleService = roleService;
    }

    public void run() {
        List<Permission> permissions = permissionRepository.findAll();
        Collection<Role> roles = roleRepository.findAll();

        Optional<Role> superAdminRole = roles.stream().filter(role -> role.getSlug().equals("super_admin")).findFirst();

        Optional<Role> adminRole = roles.stream().filter(role -> role.getSlug().equals("admin")).findFirst();
        Optional<Role> userRole = roles.stream().filter(role -> role.getSlug().equals("user")).findFirst();

        List<Permission> adminPermissions = permissions.stream()
                .filter(permission -> !permission.getSlug().contains("delete"))
                .toList();
        List<Permission> userPermissions = permissions.stream()
                .filter(permission -> !permission.getSlug().contains("delete"))
                .filter(permission -> !permission.getSlug().contains("create"))
                .filter(permission -> !permission.getSlug().contains("update"))
                .toList();

        superAdminRole.ifPresent(role -> roleService.syncPermissionsToRoleByRoleId(role.getId(), permissions));

        adminRole.ifPresent(role -> roleService.syncPermissionsToRoleByRoleId(role.getId(), adminPermissions));

        userRole.ifPresent(role -> roleService.syncPermissionsToRoleByRoleId(role.getId(), userPermissions));

        superAdminRole.ifPresent(role -> roleService.syncPermissionsToRoleByRoleId(role.getId(), permissions));

    }
}
