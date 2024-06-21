package com.QingJiu.service;

import com.QingJiu.pojo.User;
import com.QingJiu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Qing_Jiu
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-06-04 20:21:45
*/
public interface UserService extends IService<User> {

    /**
     * 登录业务
     * @param user
     * @return
     */
    Result login(User user);

    /**
     * 根据token获取用户数据
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 检测账号是不是可以注册
     * @param username
     * @return
     */
    Result checkUserName(String username);

    /**
     * 注册业务
     * @param user
     * @return
     */
    Result regist(User user);
}
