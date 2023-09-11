package com.knw.filter;

import com.alibaba.fastjson.JSON;
import com.knw.entity.Result;
import com.knw.utils.WarehouseConstants;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-08 09:26
 */
public class LoginCheckFilter implements Filter {
    private StringRedisTemplate stringRedisTemplate;

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //白名单放行
        List<String> urlList = new ArrayList<>();
        urlList.add("/login");
        urlList.add("/captcha/captchaImage");
        urlList.add("/product/img-upload");
        String url = httpServletRequest.getServletPath();
        if(urlList.contains(url)||url.contains("/img/upload")) //放开对图片的访问
        {
            chain.doFilter(request,response);
            return;//结束，让他不往下执行了，否则他会回旋回来继续执行下面的代码
        }
        //判断是否携带token，并且redis中是否有数据
        String token = httpServletRequest.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        if(StringUtils.hasText(token)&&stringRedisTemplate.hasKey(token))
        {
            //有，放行
            chain.doFilter(request,response);
            return;
        }
        //没有，说明未登录或者已过期，拦截
        Result re = Result.err(Result.CODE_ERR_UNLOGINED, "您未登录");
        String jsonString = JSON.toJSONString(re);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }
}
