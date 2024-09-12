package com.harmlessprince.todomanagerapplication.controllers;

import com.harmlessprince.todomanagerapplication.Repositories.PermissionRepository;
import com.harmlessprince.todomanagerapplication.config.CustomUserDetailService;
import com.harmlessprince.todomanagerapplication.dto.UserDto;
import com.harmlessprince.todomanagerapplication.exceptions.NotFoundException;
import com.harmlessprince.todomanagerapplication.models.Permission;
import com.harmlessprince.todomanagerapplication.models.User;
import com.harmlessprince.todomanagerapplication.Repositories.UserRepository;
import com.harmlessprince.todomanagerapplication.utils.ApiResponseHelper;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("users")
@Transactional
public class UserController {

    private final UserRepository userRepository;

    private  final PermissionRepository permissionRepository;

    private final CustomUserDetailService userDetailService;


    private final ModelMapper modelMapper;

    public UserController(UserRepository userRepository, PermissionRepository permissionRepository, CustomUserDetailService userDetailService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.userDetailService = userDetailService;
        this.modelMapper = modelMapper;
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    @PreAuthorize("hasAuthority('view_any_user')")
    public ApiResponseHelper<List<User>> index(HttpServletRequest request) {
        List<User> users = userRepository.findAll();
        ApiResponseHelper<List<User>> response = ApiResponseHelper.success(users, "User fetched successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response).getBody();
    }





    @GetMapping(path = "/{userId}")
    @PreAuthorize("hasAuthority('view_user')")
    public ApiResponseHelper<User> show(@PathVariable Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        User user = optionalUser.get();
        Collection<Permission> userPermissions = permissionRepository.findPermissionsByRolesId(user.getRole().getId());
        user.getRole().setPermissions(userPermissions);
        return ApiResponseHelper.success(user);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('create_user')")
    public ApiResponseHelper<User> store(@Valid @RequestBody UserDto userDto) {
        User user = userDetailService.registerNewUserAccount(userDto);
        return ApiResponseHelper.success(user, "User Created Successfully");
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("hasAuthority('update_user')")
    public ApiResponseHelper<Object> update(@RequestBody UserDto userDto, @PathVariable Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        //unset user password before performing update
        if (userDto.getPassword() != null){
            userDto.setPassword(null);
        }
        User user = optionalUser.get();
        modelMapper.map(userDto, user);
        User updatedUser = userRepository.save(user);
        return ApiResponseHelper.success(updatedUser, "User updated Successfully");
    }



    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('delete_user')")
    public ApiResponseHelper<Object> destroy(HttpServletRequest request, @PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        userRepository.delete(user.get());
        return ApiResponseHelper.success(null, "User deleted successfully");
    }
}
