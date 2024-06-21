package com.QingJiu.service.impl;

import com.QingJiu.utils.JwtHelper;
import com.QingJiu.utils.MD5Util;
import com.QingJiu.utils.Result;
import com.QingJiu.utils.ResultCodeEnum;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.QingJiu.pojo.User;
import com.QingJiu.service.UserService;
import com.QingJiu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author Qing_Jiu
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-06-04 20:21:45
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 登录业务功能的实现
     *
     * 1、根据账号，查找用户对象 -loginUser
     * 2、如果用户对象为null，查询失败，账号错误 501
     * 3、对比，密码  失败，返回503错误
     * 4、根据用户id生成一个token， token - > result（会在客户端缓存） 返回
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {

        //根据账号查询数据库
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());

        User selectOne = userMapper.selectOne(lambdaQueryWrapper);

        //如果账号为null
        if (selectOne == null){
            //返回统一结果；在统一结果中允许有枚举类型，对枚举类型的code进行解析
            //放入到result的message上
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //成功对比密码
        if (!StringUtils.isEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(selectOne.getUserPwd())){//对用户登录的密码进行MD5加密与查找到的用户密码进行对比
            //登陆成功

            //根据用户id生成 token
            String token = jwtHelper.createToken(Long.valueOf(selectOne.getUid()));
            //将toke封装到result返回
            Map map = new HashMap<>();
            map.put("token",token);

            return Result.ok(map);
        }


        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 根据token获取用户数据
     *
     * 1、token是否在有效期
     * 2、根据token解析userId
     * 3、根据用户id查询用户数据
     * 4、去掉密码，封装到result结果返回即可
     * @param token
     * @return
     */

    @Override
    public Result getUserInfo(String token) {

        //检验token是否过期
        //true过期
        boolean expiration = jwtHelper.isExpiration(token);

        if (expiration){
            //未登录
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

        //获取用户id
        int userId = jwtHelper.getUserId(token).intValue();

        //根据用户id查询
        User user = userMapper.selectById(userId);
        //将查询出用户密码修改
        user.setUserPwd("");

        //将查询和修改后的数据传入到map中
        Map map = new HashMap<>();
        map.put("loginUser",user);

        //在通过统一返回结果输出
        return Result.ok(map);
    }

    /**
     * 检查账号是否可用
     * 1、根据账号进行count查询
     * 2、count == 0 可用
     * 3、count > 0 不可用
     * @param username
     * @return
     */
    @Override
    public Result checkUserName(String username) {

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);

        Long count = userMapper.selectCount(lambdaQueryWrapper);

        if (count == 0){
            return Result.ok(null);
        }


        return Result.build(null, ResultCodeEnum.USERNAME_USED);
    }

    /**
     * 注册业务
     * 1、依然检查账号是否已经被注册
     * 2、密码加密处理
     * 3、账号数据保存
     * 4、返回结果
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());

        Long count = userMapper.selectCount(lambdaQueryWrapper);

        if (count > 0){
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));

        int insert = userMapper.insert(user);
        System.out.println("insert = " + insert);

        return Result.ok(null);
    }
}




