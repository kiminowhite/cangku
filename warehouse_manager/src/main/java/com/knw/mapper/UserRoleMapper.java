package com.knw.mapper;

import com.knw.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-09 17:29
 */
public interface UserRoleMapper {
    public List<Integer> queryUserRoleIdById(Integer userId);

    //根据用户id删除已分配的用户角色关系
    public int removeUserRoleByUid(Integer userId);

    public int insertUserRole(@Param("userRole") UserRole userRole );
}
