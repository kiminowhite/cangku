package com.knw.service;

import com.knw.entity.Auth;
import com.knw.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthService {

	//根据用户id查询用户权限(菜单)树的业务方法
	public List<Auth> findAuthTree(int userId);

    public List<Auth> findAuthTree();
}
