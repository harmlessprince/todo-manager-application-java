package com.harmlessprince.todomanagerapplication.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role extends BaseModel {

    @NotBlank
    @Column(unique = true, nullable = false)
    private String name;

    private Boolean status = true;

    @Column(unique = true, nullable = false)
    private String slug;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
//    @JsonIgnore
    @ToString.Exclude
    @JsonBackReference
//    @ToString.Exclude
    private Collection<User> users;


//    @Getter(AccessLevel.NONE)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id", referencedColumnName = "id"))
    private Collection<Permission> permissions;

//    public Collection<Permission> rolePermissions() {
//        return this.getPermissions();
//    }


    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Role(String name) {
        this.name = name;
        this.setSlug(name);
    }

    public void setSlug(String slug) {
        this.slug = slug.replace(" ", "_").toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
