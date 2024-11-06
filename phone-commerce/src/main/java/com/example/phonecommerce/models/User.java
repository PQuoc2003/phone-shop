package com.example.phonecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String email;

    private String userName;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Column(nullable = false)
    @NotEmpty
    private String password;


    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + "]";
    }

}
