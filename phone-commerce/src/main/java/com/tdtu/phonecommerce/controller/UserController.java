package com.tdtu.phonecommerce.controller;


import com.tdtu.phonecommerce.models.Roles;
import com.tdtu.phonecommerce.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @GetMapping("/manager/employee/add")
    public String getAddUserPage(Model model){
        model.addAttribute("newUser", new User());

        List<Roles> roles = new ArrayList<>();

        roles.add(Roles.ROLES_EMPLOYEE);
        roles.add(Roles.ROLES_MANAGER);
        roles.add(Roles.ROLES_USER);
        roles.add(Roles.ROLES_BLOGGER);

        model.addAttribute("roles", roles);


        return "manager_template/add_employee";

    }






}
