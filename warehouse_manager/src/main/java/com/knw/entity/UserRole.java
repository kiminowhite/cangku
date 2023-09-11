package com.knw.entity;

import lombok.*;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-09 17:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole {
    @Getter
    private int userRoleId;
    private int roleId;
    @Getter
    private int userId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
