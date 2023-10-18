package ru.panov.eshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity{
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role roles;
    private boolean archive;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Bucket bucket;
}
