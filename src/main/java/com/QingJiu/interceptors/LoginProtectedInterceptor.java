package com.QingJiu.interceptors;


import com.QingJiu.utils.JwtHelper;
import com.QingJiu.utils.Result;
import com.QingJiu.utils.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登录包含拦截器，检查请求头是否包含有效token
 *      有效访问 放行
 *      无效访问 拦截
 */

@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //从请求头中获取token
        String token = request.getHeader("token");//从请求头中获取toke
        //检查是否有效
        boolean expiration = jwtHelper.isExpiration(token);

        //有效放行
        if (!expiration){
            //放行执行
            return true;
        }
        //无效返回504的状态json
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);
        response.getWriter().print(json);//返回客户机json数据

        return false;
    }
}
