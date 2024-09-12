package com.harmlessprince.todomanagerapplication.services;

import com.harmlessprince.todomanagerapplication.Repositories.RoleRepository;
import com.harmlessprince.todomanagerapplication.Repositories.UserRepository;
import com.harmlessprince.todomanagerapplication.models.Permission;
import com.harmlessprince.todomanagerapplication.models.Role;
import com.harmlessprince.todomanagerapplication.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final EntityManagerFactory entityManagerFactory;

    public RoleService(RoleRepository roleRepository,
                       UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.entityManagerFactory = entityManagerFactory;
    }



    public List<String> getRolePermissions(Role role){
        List<String> permissions = new ArrayList<>();
        List<Permission> collection  = role.getPermissions().stream().toList();
        for (Permission item : collection) {
            permissions.add(item.getSlug());
        }
        return permissions;
    }


    public void syncPermissionsToRoleByRoleId(Long roleId, List<Permission> permissions){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
       Role role = entityManager.find(Role.class,Math.toIntExact(roleId));

        role.getPermissions().clear();

       role.setPermissions(permissions);
        roleRepository.save(role);
        entityManager.close();
    }
    public void syncPermissionsToRoleByRole(Role role, List<Permission> permissions){
        role.getPermissions().clear();
        role.getPermissions().addAll(permissions);
    }

    public void syncRoleToUser(User user, Role role){
        user.setRole(role);
        userRepository.save(user);
    }
}
