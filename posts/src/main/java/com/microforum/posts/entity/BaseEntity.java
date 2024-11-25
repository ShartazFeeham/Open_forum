package com.microforum.posts.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@MappedSuperclass
public class BaseEntity  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    @Schema(description = "Id of the entity", example = "1")
    protected Long id;

    @CreationTimestamp
    @Column(updatable = false)
    @Schema(description = "Creation date of the entity", example = "2021-01-01T00:00:00")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Schema(description = "Update date of the entity", example = "2021-01-01T00:00:00")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseEntity that)) {
            return false;
        }
        return id.equals(that.id);
    }
}