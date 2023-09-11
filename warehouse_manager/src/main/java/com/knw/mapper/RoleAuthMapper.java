package com.knw.mapper;

import com.knw.entity.RoleAuth;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 01:14
 */
public interface RoleAuthMapper {

    public int removeRoleAuthByRid(Integer roleId);
    public int insertRoleAuth(RoleAuth roleAuth);
    public List<RoleAuth> selectRoleAuthByRid(Integer roleId);
}
