package com.tdtu.phonecommerce.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PasswordDTO {

    private String currentPassword;
    private String newPassword;
    private String confPassword;

}
