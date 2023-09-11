package com.knw.controller;

import com.knw.dto.AssignAuthDto;
import com.knw.entity.Auth;
import com.knw.entity.Result;
import com.knw.service.AuthService;
import com.knw.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 00:09
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    RoleService roleService;
    @Autowired
    AuthService authService;
    @GetMapping("/auth-tree")
    public Result getAuthTree()
    {
        List<Auth> authTree = authService.findAuthTree();
        return Result.ok(authTree);
    }

}
