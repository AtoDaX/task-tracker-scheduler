package edu.pet.tasktrackerscheduler.scheduler.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)

public class Task {
    @Id
    @Column(nullable = false)
    @GeneratedValue
    private UUID id;
    @NotBlank
    @Column(nullable = false)
    private final String title;
    @NotNull
    @Column(nullable = false)
    private final String details;
    @Column(nullable = false)
    private boolean completed;
    @Column(nullable = true)
    private Timestamp completedAt;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }



}
