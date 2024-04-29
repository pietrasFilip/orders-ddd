package com.app.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "verification_tokens")
public class VerificationTokenEntity extends BaseEntity {
    private String token;
    private Long timestamp;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", unique = true)
    @Getter
    private UserEntity user;

    public Optional<UserEntity> validate() {
        return Optional.ofNullable(timestamp >= System.nanoTime() ? user : null);
    }
}
