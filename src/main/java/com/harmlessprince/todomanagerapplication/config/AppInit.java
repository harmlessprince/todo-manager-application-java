package com.harmlessprince.todomanagerapplication.config;

import com.harmlessprince.todomanagerapplication.seeders.PermissionSeeder;
import com.harmlessprince.todomanagerapplication.seeders.RolePermissionSeeder;
import com.harmlessprince.todomanagerapplication.seeders.RoleSeeder;
import com.harmlessprince.todomanagerapplication.seeders.UserSeeder;
import com.harmlessprince.todomanagerapplication.services.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
//@DependsOn({"RoleService"})
public class AppInit {

    private final PermissionSeeder permissionSeeder;
    private final RoleSeeder roleSeeder;

    private final UserSeeder userSeeder;

    private final RolePermissionSeeder rolePermissionSeeder;

    public AppInit(PermissionSeeder permissionSeeder, RoleSeeder roleSeeder, UserSeeder userSeeder, RolePermissionSeeder rolePermissionSeeder) {
        this.permissionSeeder = permissionSeeder;
        this.roleSeeder = roleSeeder;
        this.userSeeder = userSeeder;
        this.rolePermissionSeeder = rolePermissionSeeder;
    }

    @PostConstruct
    private void seed() {
        permissionSeeder.run();
        roleSeeder.run();
        rolePermissionSeeder.run();
        userSeeder.run();
    }

}
