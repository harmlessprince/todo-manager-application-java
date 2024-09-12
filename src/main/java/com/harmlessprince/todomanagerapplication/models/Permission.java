package com.harmlessprince.todomanagerapplication.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "permissions")
public class Permission extends BaseModel {

    @NotBlank
    @Column(unique = true, nullable = false)
    private String name;

    private Boolean status = true;
    @Column(unique = true, nullable = false)
    private String slug;

//    @JsonIgnore
    @JsonBackReference
    @ManyToMany(mappedBy = "permissions",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            })
    Collection<Role> roles;

    public Permission(String name) {
        this.name = name;
        this.setSlug(name);
    }

    public void setSlug(String slug) {
        this.slug = slug.replace(" ", "_").toLowerCase();
    }
}
