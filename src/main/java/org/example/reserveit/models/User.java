package org.example.reserveit.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseModel {
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private Long phoneNumber;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType type;
}
