package com.harmlessprince.todomanagerapplication.seeders;

import com.harmlessprince.todomanagerapplication.Repositories.RoleRepository;
import com.harmlessprince.todomanagerapplication.models.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleSeeder {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void run(){
        List<Role> roles
                = new ArrayList<>();

        roles.add(new Role("Admin"));
        roles.add(new Role("Super Admin"));
        roles.add(new Role("User"));
        if (roleRepository.count() <= 0) {
            roleRepository.saveAll(roles);
        }
    }
}
