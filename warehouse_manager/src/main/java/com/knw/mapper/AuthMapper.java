package com.knw.mapper;

import java.util.List;
import com.knw.entity.Auth;

public interface AuthMapper {

	//根据用户id查询用户所有权限(菜单)的方法

	public List<Auth> findAuthByUid(int userId);
	public List<Auth>  findAllauth();

}
