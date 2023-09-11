package com.knw.service.impl;

import com.knw.dto.AssignRoleDto;
import com.knw.entity.Result;
import com.knw.entity.User;
import com.knw.entity.UserRole;
import com.knw.mapper.RoleMapper;
import com.knw.mapper.UserMapper;
import com.knw.mapper.UserRoleMapper;
import com.knw.service.UserService;
import com.knw.utils.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.knw.page.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-08 00:25
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserRoleMapper userRoleMapper;


    @Override
    public User queryUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }


    @Override
    public Page queryUserByPage(Page page, User user) {
        Integer count = userMapper.findUserRowCount(user);
        List<User> userByPage = userMapper.findUserByPage(user, page);
        page.setTotalNum(count);
        page.setResultList(userByPage);
        return page;

    }

    @Override
    public Result saveUser(User user) {
        //判断用户（usercode）是否已存在，如果存在了就不能再存了
        User userByCode = userMapper.findUserByCode(user.getUserCode());
        if(userByCode!=null)
        {
            return Result.err(Result.CODE_ERR_BUSINESS,"账号已存在");
        }

        //密码加密
        String userPwd = user.getUserPwd();
        userPwd = DigestUtil.hmacSign(userPwd);
        user.setUserPwd(userPwd);

        int i = userMapper.insertUser(user);
       if(i>0)
       {
           return Result.ok("用户"+user.getUserName()+"添加成功");
       }
       return Result.err(Result.CODE_ERR_BUSINESS,"用户添加失败");
    }

    @Override
    public Result setUserState(User user) {
        int i = userMapper.setStateById(user.getUserId(), user.getUserState());
        if(i>0)
        {
            return  Result.ok("启用或禁用成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"启用或禁用失败！");
    }

    @Override
    @Transactional
    public void assignRole(AssignRoleDto assignRoleDto) {
        userRoleMapper.removeUserRoleByUid(assignRoleDto.getUserId());


            List<String> roleCheckList = assignRoleDto.getRoleCheckList();
            for(String roleName: roleCheckList) {
                Integer roleId = roleMapper.findRoleByName(roleName);
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(assignRoleDto.getUserId());
                userRoleMapper.insertUserRole(userRole);
            }

    }

    @Override
    public Result removeUserByIds(List<Integer> userIdList) {
        int i = userMapper.setIsDeleteByUids(userIdList);
        if(i>0)
        {
            return  Result.ok("用户成功删除");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"用户删除失败");
    }

    @Override
    public Result setUserNameById(User user) {
        int i = userMapper.setUserNameById(user);
        if(i>0)
        {
            return Result.ok("修改名称成功！");
        }
        return  Result.err(Result.CODE_ERR_BUSINESS,"修改失败!");
    }

    @Override
    public Result resetUserPwdById(Integer userId) {
        String password = DigestUtil.hmacSign("123456");
        int i = userMapper.updatePwdById(userId, password);
        if(i>0)
        {
            return Result.ok("重设密码成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"重设密码失败！");
    }
}
