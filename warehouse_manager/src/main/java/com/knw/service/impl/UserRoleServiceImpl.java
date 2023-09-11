package com.knw.service.impl;

import com.knw.mapper.UserRoleMapper;
import com.knw.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-09 17:34
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleMapper userRoleMapper;
    @Override
    public List<Integer> queryUserRoleIds(Integer userId) {
        return userRoleMapper.queryUserRoleIdById(userId);
    }
}
