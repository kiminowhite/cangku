package com.knw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 01:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAuth {
    public int roleAuthId;
    public int roleId;
    public int authId;

    public RoleAuth(int roleId, int authId) {
        this.roleId = roleId;
        this.authId = authId;
    }
}
