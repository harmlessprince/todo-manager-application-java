package com.harmlessprince.todomanagerapplication.Repositories;

import com.harmlessprince.todomanagerapplication.models.Permission;
import com.harmlessprince.todomanagerapplication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    public Permission findBySlug(String slug);

    List<Permission> findPermissionsByRolesId(Long roleId); //returns all permissions related to role id
}

//https://www.bezkoder.com/jpa-many-to-many/