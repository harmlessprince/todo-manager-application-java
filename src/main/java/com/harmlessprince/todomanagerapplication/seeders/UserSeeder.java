package com.harmlessprince.todomanagerapplication.seeders;


import com.harmlessprince.todomanagerapplication.Repositories.RoleRepository;
import com.harmlessprince.todomanagerapplication.Repositories.UserRepository;
import com.harmlessprince.todomanagerapplication.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void run() {
        List<User> users
                = new ArrayList<>();
        User admin = new User("Admin",
                "User",
                "admin_user",
                "admin@gmail.com",
                passwordEncoder.encode("password")
        );
        admin.setRole(roleRepository.findBySlug("admin"));
//
        User superAdmin = new User("Super",
                "Admin",
                "super_admin_user",
                "superadmin@gmail.com",
                passwordEncoder.encode("password")
        );
        superAdmin.setRole(roleRepository.findBySlug("super_admin"));

        User normalUser = new User("Normal",
                "User",
                "user",
                "user@gmail.com",
                passwordEncoder.encode("password")
        );
        normalUser.setRole(roleRepository.findBySlug("user"));
        users.add(superAdmin);
        users.add(admin);
        users.add(normalUser);
        if (userRepository.count() <= 0) {
            userRepository.saveAll(users);
        }
    }
}
