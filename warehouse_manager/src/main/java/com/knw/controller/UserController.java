package com.knw.controller;

import com.knw.dto.AssignRoleDto;
import com.knw.entity.*;
import com.knw.page.Page;
import com.knw.service.AuthService;
import com.knw.service.RoleService;
import com.knw.service.UserRoleService;
import com.knw.service.UserService;
import com.knw.utils.DigestUtil;
import com.knw.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.zip.ZipEntry;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-08 10:58
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    AuthService authService;
    @Autowired
    TokenUtils tokenUtils;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleService userRoleService;

    @GetMapping("auth-list")
    public Result authList(@RequestHeader String token)
    {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        Integer userId = currentUser.getUserId();
        List<Auth> authTree = authService.findAuthTree(userId);
        return Result.ok(authTree);

    }
    @GetMapping("/user-list")
    public Result userList(User user, Page page)
    {
        //没参数，查询所有用户，再分页    有参数，条件查找，再分页
        page = userService.queryUserByPage(page, user);

        return Result.ok(page);
    }
    @PostMapping("addUser")
    public  Result addUser(@RequestBody User user,@RequestHeader String token)

    {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //设置相关信息
        user.setCreateBy(currentUser.getUserId());
        Result result = userService.saveUser(user);
        return result;

    }
    @PutMapping("/updateState")
    public Result updateUserState(@RequestBody User user)
    {
        Result result = userService.setUserState(user);
        return result;
    }
    @GetMapping("/user-role-list/{userId}")
    public Result getUserRole(@PathVariable("userId")Integer userId)
    {
        List<Role> roles = roleService.queryRolesByUserId(userId);
        return Result.ok(roles);
    }
    @PutMapping("/assignRole")
    public Result assignRole(@RequestBody AssignRoleDto assignRoleDto)
    {
     userService.assignRole(assignRoleDto);
     return Result.ok("分配角色成功");
    }

    @DeleteMapping("/deleteUser/{userId}")
    public Result deleteUserById(@PathVariable("userId")Integer userId)
    {
        List<Integer> ids = new ArrayList<>();
        ids.add(userId);
        Result result = userService.removeUserByIds(ids);
        return  result;
    }
    @DeleteMapping("/deleteUserList")
    public Result deleteUserByIds(@RequestBody List<Integer> userIdList)
    {
        Result result = userService.removeUserByIds(userIdList);
        return result;
    }

    @PutMapping("/updateUser")
    public  Result updateUser(@RequestBody User user,@RequestHeader String token)
    {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        user.setUpdateBy(currentUser.getUserId());
        Result result = userService.setUserNameById(user);
        return result;
    }
    @PutMapping("/updatePwd/{userId}")
    public Result updatePwd(@PathVariable("userId") Integer userId)
    {
        Result result = userService.resetUserPwdById(userId);
        return result;

    }
}




