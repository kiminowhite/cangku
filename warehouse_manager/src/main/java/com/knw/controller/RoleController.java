package com.knw.controller;

import com.knw.dto.AssignAuthDto;
import com.knw.entity.Auth;
import com.knw.entity.CurrentUser;
import com.knw.entity.Result;
import com.knw.entity.Role;
import com.knw.page.Page;
import com.knw.service.AuthService;
import com.knw.service.RoleService;
import com.knw.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-09 16:03
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    TokenUtils tokenUtils;
    @Autowired
    RoleService roleService;
    @Autowired
    AuthService authService;
    @GetMapping("/role-list")
    public Result roleList()
    {
        List<Role> allRole = roleService.getAllRole();
        return Result.ok(allRole);
    }
    @GetMapping("/role-page-list")
    public  Result rolePageList(Role role, Page page)
    {
        page = roleService.queryRolePage(page, role);
        return Result.ok(page);
    }
    @PostMapping("role-add")
    public  Result roleAdd(@RequestBody Role role,@RequestHeader String token)

    {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        role.setCreateBy(currentUser.getUserId());

        Result result = roleService.saveRole(role);
        return result;
    }
    @PutMapping("/role-state-update")
    public Result roleStateUpdate(@RequestBody Role role)
    {
        return roleService.updateRoleState(role);
    }

    @DeleteMapping("/role-delete/{userId}")
    public  Result roledelete(@PathVariable("userId") Integer userId)
    {
        return roleService.deleteRole(userId);
    }
    @GetMapping("/role-auth")
    public Result roleauth(@RequestParam("roleId")Integer roleId)
    {
        List<Integer> auths = roleService.queryAuthIds(roleId);
        return Result.ok(auths);
    }
    @PutMapping("/auth-grant")
    public Result grantAuth(@RequestBody AssignAuthDto assignAuthDto)
    {
        roleService.saveRoleAuth(assignAuthDto);
        return Result.ok("权限分配成功");

    }
    @PutMapping("/role-update")
    public Result roleUpdate(@RequestBody Role role,@RequestHeader String token)
    {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        role.setUpdateBy(currentUser.getUserId());
        role.setUpdateTime(new Date());
        Result result = roleService.updateRoleDesc(role);
        return  result;

    }
}
