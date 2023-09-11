package com.knw.controller;

import ch.qos.logback.core.joran.conditional.ElseAction;
import com.knw.entity.CurrentUser;
import com.knw.entity.LoginUser;
import com.knw.entity.Result;
import com.knw.entity.User;
import com.knw.service.UserService;
import com.knw.utils.DigestUtil;
import com.knw.utils.TokenUtils;
import com.knw.utils.WarehouseConstants;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-07 22:31
 */
@RestController
public class LoginController {
    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private UserService userService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @RequestMapping("/login")
    public Result login(@RequestBody LoginUser loginUser)
    {
        //校验验证码
        String code = loginUser.getVerificationCode();
        Boolean result = stringRedisTemplate.hasKey(code);
        if(result==false)
        {
            return Result.err(Result.CODE_ERR_BUSINESS,"認証コードエラー");
        }

        User user = userService.queryUserByCode(loginUser.getUserCode());
        if(user==null){
            //账号不存在
            return  Result.err(Result.CODE_ERR_BUSINESS,
                    "アカウントは存在しません");
        }else
        {
           //账号存在
            // 是否被审核（只有审核过的用户才能登录）
            if(user.getUserState().equals(WarehouseConstants.USER_STATE_PASS))
            {
                //用户已审核
                //校验密码，但是要先把明文密码加密
                String userPwd = loginUser.getUserPwd();
                 userPwd = DigestUtil.hmacSign(userPwd);
                 if(userPwd.equals(user.getUserPwd()))
                 {
                     //密码一致
                     //登录，生成jwt token(此方法已经将jwt也存到了redis中）
                     CurrentUser currentUser = new CurrentUser(user.getUserId(), user.getUserCode(),user.getUserName());
                     String token = tokenUtils.loginSign(currentUser, userPwd);
                     return Result.ok("ログイン完了",token);

                 }else {
                     //密码不一致
                     return  Result.err(Result.CODE_ERR_BUSINESS,"パスワードエラー");
                 }

            }else{
             //用户未审核
            return Result.err(Result.CODE_ERR_BUSINESS,"ユーザーがレビューしていません");
            }

        }
    }
    @GetMapping("/curr-user")
    public  Result currentUser(@RequestHeader String token) //获取请求头，类似@Requestp获取参数一样
    {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        return Result.ok(currentUser);
    }

    @DeleteMapping("/logout")
    public Result logout(@RequestHeader String token)
    {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        stringRedisTemplate.delete(token);
        return Result.ok("用户"+currentUser.getUserName()+"退出成功！");
    }
}
