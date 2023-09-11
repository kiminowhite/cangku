package com.knw.service.impl;

import com.knw.dto.AssignAuthDto;
import com.knw.dto.AssignRoleDto;
import com.knw.entity.Result;
import com.knw.entity.Role;
import com.knw.entity.RoleAuth;
import com.knw.mapper.AuthMapper;
import com.knw.mapper.RoleAuthMapper;
import com.knw.mapper.RoleMapper;
import com.knw.page.Page;
import com.knw.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-09 16:02
 */

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    AuthMapper authMapper;
    @Autowired
    RoleAuthMapper roleAuthMapper;

    @Override
    public List<Role> getAllRole() {
        List<Role> allRole = roleMapper.findAllRole();
        return allRole;
    }

    @Override
    public List<Role> queryRolesByUserId(Integer userId) {
        List<Role> rolesByUserId = roleMapper.findRolesByUserId(userId);
        return rolesByUserId;
    }


    @Override
    public Page queryRolePage(Page page, Role role) {
        int roleCount = roleMapper.selectRoleCount(role);
        List<Role> roles = roleMapper.selectRolePage(page, role);
        page.setResultList(roles);
        page.setTotalNum(roleCount);
        return page;
    }

    @Override
    public Result saveRole(Role role) {
        //判断用户是否存在
        Role checkrole = roleMapper.findRoleByNameOrCode(role.getRoleName(), role.getRoleCode());
        if (checkrole != null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "不能添加已存在的角色");
        }
        int i = roleMapper.insertRole(role);
        if (i > 0) {
            return Result.ok("添加角色成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "添加角色失败");
    }

    @Override
    public Result updateRoleState(Role role) {
        int i = roleMapper.updateRoleState(role);
        if (i > 0) {
            return Result.ok("启用或禁用该用户成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "启用或禁用该用户失败");
    }


    @Override
    @Transactional
    public Result deleteRole(Integer roleId) {
        int i = roleMapper.deleteRoleById(roleId);
        if (i > 0) {
            return Result.ok("删除角色成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "删除角色失败");
    }

    @Override
    public Result updateRoleDesc(Role role) {
        int i = roleMapper.updateDescById(role);
        if(i>0)
        {
            return Result.ok("修改用户描述成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改用户描述失败");
    }

    @Override
    @Transactional
    public void saveRoleAuth(AssignAuthDto assignAuthDto) {
        //先删掉原有的，在添加现在的
        //先判断是否有原有的
        List<RoleAuth> roleAuths = roleAuthMapper.selectRoleAuthByRid(assignAuthDto.getRoleId());
        if(roleAuths!=null)
        {
            roleAuthMapper.removeRoleAuthByRid(assignAuthDto.getRoleId());
        }

        roleAuthMapper.removeRoleAuthByRid(assignAuthDto.getRoleId());



        List<Integer> authIdList = assignAuthDto.getAuthIds();
        for (Integer authid : authIdList) {
            RoleAuth roleAuth =new RoleAuth(assignAuthDto.getRoleId(),authid);
            roleAuthMapper.insertRoleAuth(roleAuth);
        }
    }

        @Override
        public List<Integer> queryAuthIds (Integer roleId)
        {
            List<Integer> authIds = roleMapper.findAuthIds(roleId);

            return authIds;

        }


    }

