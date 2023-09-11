package com.knw.mapper;

import com.knw.entity.User;
import com.knw.page.Page;
import org.apache.ibatis.annotations.Param; //必须导入这个包才行

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-08 00:15
 */
public interface  UserMapper {
    //根据账号查找用户
    public User findUserByCode(String userCode);
    public List<User>  findUserByPage(@Param("user")User user, @Param("page")Page page); //要传分页参数
    public Integer findUserRowCount( User user);
    // 获取总行数，配合以上方法
    public int insertUser(User user);
    public  int setStateById(Integer userId,String userState);

    public int setIsDeleteByUids (List<Integer> userIdList);

    public int setUserNameById(User user);

    public int updatePwdById(Integer userId,String password);




}
