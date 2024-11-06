package com.example.phonecommerce.dto;

import com.example.phonecommerce.models.Roles;
import lombok.Data;


@Data
public class UserDTO {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String address;

    private String phoneNumber;

    private Roles roles;


}
