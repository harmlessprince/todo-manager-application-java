package com.harmlessprince.todomanagerapplication.config;

import com.harmlessprince.todomanagerapplication.Repositories.PermissionRepository;
import com.harmlessprince.todomanagerapplication.dto.UserDto;
import com.harmlessprince.todomanagerapplication.exceptions.EmailExistsException;
import com.harmlessprince.todomanagerapplication.models.Permission;
import com.harmlessprince.todomanagerapplication.models.Role;
import com.harmlessprince.todomanagerapplication.models.User;
import com.harmlessprince.todomanagerapplication.Repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {


    private final UserRepository userRepository;

    private final PermissionRepository permissionRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public CustomUserDetailService(UserRepository userRepository, PermissionRepository permissionRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.entityManager = entityManager;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username supplied");
        }
        return user.get();
    }

    public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException {
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException("email has already been taken");
        }
        if (userNameExists(accountDto.getUsername())) {
            throw new EmailExistsException("username has already been taken");
        }
        if (phoneNumberExists(accountDto.getPhoneNumber())) {
            throw new EmailExistsException("phone number has already been taken");
        }
        accountDto.setPassword(bCryptPasswordEncoder().encode(accountDto.getPassword()));
        User user = toEntity(accountDto);
//      user.setRole(new Role(Integer.valueOf(1), user));
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    private boolean userNameExists(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    private boolean phoneNumberExists(String phoneNumber) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        return user.isPresent();
    }

    @Bean
    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static User toEntity(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper.map(userDto, User.class);
    }

    public void updateLasLogin(User user) {
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }


    public Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> query = criteriaBuilder.createQuery(Role.class);
        Root<Role> roleRoot = query.from(Role.class);
        query.select(roleRoot);
        roleRoot.fetch("permissions");
        query.where(criteriaBuilder.equal(roleRoot.get("id"), role.getId()));
        Role newRole = entityManager.createQuery(query).getSingleResult();
        String userRole = newRole.getSlug();
        List<String> permissions = new ArrayList<>();
        Collection<Permission> rolPermissions = permissionRepository.findPermissionsByRolesId(role.getId());
        List<Permission> collection = rolPermissions.stream().toList();
        for (Permission item : collection) {
            permissions.add(item.getSlug());
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(userRole));
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
        System.out.println("Authorities !!!!!!!!!!!!!!!!!!");
        System.out.println(authorities);
        return authorities;
    }

//    private List<String> getPermissions(Role role) {
//        List<String> permissions = new ArrayList<>();
//        List<Permission> collection = role.getPermissions().stream().toList();
//        for (Permission item : collection) {
//            permissions.add(item.getSlug());
//        }
//        return permissions;
//    }


}
