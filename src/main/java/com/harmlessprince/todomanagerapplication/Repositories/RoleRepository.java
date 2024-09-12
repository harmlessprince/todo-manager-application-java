package com.harmlessprince.todomanagerapplication.Repositories;

import com.harmlessprince.todomanagerapplication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Role findBySlug(String slug);
    public List<Role> findRolesByPermissionsId(Long permissionId); //returns all roless related to a permission id
}


//https://www.bezkoder.com/jpa-many-to-many/