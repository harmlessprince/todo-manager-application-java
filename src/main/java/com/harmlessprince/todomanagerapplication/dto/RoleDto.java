package com.harmlessprince.todomanagerapplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.harmlessprince.todomanagerapplication.models.Role} entity
 */
@Data
@AllArgsConstructor
public class RoleDto implements Serializable {
    private final Long id;
    private final LocalDateTime created_at;
    private final LocalDateTime updated_at;

    @NotBlank
    private final String name;
    private final Boolean status;
    private String slug;

    public void setSlug(String slug) {
        this.slug  = slug.replace(" ", "-").toLowerCase();
    }
}