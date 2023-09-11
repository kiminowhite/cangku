package com.knw.service;

import com.knw.dto.AssignRoleDto;
import com.knw.entity.Result;
import com.knw.entity.User;
import com.knw.page.Page;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-08 00:24
 */
public interface UserService {
    public User queryUserByCode(String userCode);
    public Page queryUserByPage(Page page, User user);

    public Result saveUser(User user);
    public Result setUserState(User user);

    public void assignRole(AssignRoleDto assignRoleDto);

    public Result removeUserByIds(List<Integer> userIdList);

    public  Result setUserNameById(User user);

    public  Result resetUserPwdById(Integer userId);
}
