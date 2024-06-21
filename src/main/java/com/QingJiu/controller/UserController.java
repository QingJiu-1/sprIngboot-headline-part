package com.QingJiu.controller;

import com.QingJiu.pojo.User;
import com.QingJiu.service.UserService;
import com.QingJiu.utils.JwtHelper;
import com.QingJiu.utils.Result;
import com.QingJiu.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin//跨域
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(@RequestBody User user){ //返回给全局统一返回类 Json接值通过@RequestBody

        //只做接收和返回结果
        Result result = userService.login(user);

        return result;
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token){//拿到请求头

       Result result = userService.getUserInfo(token);

       return result;
    }

    @PostMapping("checkUserName")
    public Result checkUserName(String username){

       Result result = userService.checkUserName(username);
       return result;

    }

    @PostMapping("regist")
    public Result regist(@RequestBody User user){
      Result result = userService.regist(user);
      return result;
    }

    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){

        boolean expiration = jwtHelper.isExpiration(token);

        if (expiration){
            //已经过期了
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        return Result.ok(null);

    }

}
