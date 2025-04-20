package com.agendzy.api.core.domain.common;

import com.agendzy.api.util.IDGeneratorUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@AllArgsConstructor
@Getter
@Setter
public class BaseEntity implements Serializable {

    @Id
    @Column(length = 64)
    private String id;

    @Column(length = 64)
    private String tenant;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected BaseEntity() {
        id = IDGeneratorUtils.id();
    }

    protected BaseEntity(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof BaseEntity baseEntity) {
            return Objects.equals(id, baseEntity.id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "tenant = " + tenant + ", " +
                ")";
    }

}
