package com.harmlessprince.todomanagerapplication.seeders;

import com.harmlessprince.todomanagerapplication.Repositories.PermissionRepository;
import com.harmlessprince.todomanagerapplication.models.Permission;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionSeeder {

    private final PermissionRepository permissionRepository;

    public PermissionSeeder(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public void run(){
        List<Permission> permissions
                = new ArrayList<>();

        permissions.add(new Permission("Create user"));
        permissions.add(new Permission("Update user"));
        permissions.add(new Permission("View user"));
        permissions.add(new Permission("View any user"));
        permissions.add(new Permission("Delete user"));


        permissions.add(new Permission("Create task"));
        permissions.add(new Permission("Update task"));
        permissions.add(new Permission("View task"));
        permissions.add(new Permission("View any task"));
        permissions.add(new Permission("Delete task"));


        if (permissionRepository.count() <= 0) {
            permissionRepository.saveAll(permissions);
        }
    }
}
