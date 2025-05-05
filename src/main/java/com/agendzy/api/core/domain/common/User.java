package com.agendzy.api.core.domain.common;

import com.agendzy.api.util.IDGeneratorUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @Column(length = 64)
    private String id;

    @Column(length = 150)
    private String fullName;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(unique = true, length = 20)
    private String phoneNumber;

    private String password;

    @Column(nullable = false)
    private boolean creationCompleted = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User() {
        id = IDGeneratorUtils.id();
    }

    @Override
    public boolean equals(Object otherEntity) {
        if (this == otherEntity) {
            return true;
        }

        if (otherEntity instanceof User user) {
            return Objects.equals(id, user.id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
