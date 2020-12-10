package com.github.mzkb.survey.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 6542773107009002489L;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @NotBlank
    @Column(name = "uuid", nullable = false, unique = true, updatable = false, length = 36)
    private String uuid;

    @NotNull
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @NotNull
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatedAsString() {
        return localDateTimeAsString(created);
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getUpdatedAsString() {
        return localDateTimeAsString(updated);
    }

    @PrePersist
    public void prePersist() {
        if (uuid == null || uuid.isEmpty() || uuid.isBlank()) {
            uuid = randomUUID().toString();
        }

        if (created == null) {
            created = now();
        }

        if (updated == null) {
            updated = now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updated = now();
    }

    private String localDateTimeAsString(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }
}
