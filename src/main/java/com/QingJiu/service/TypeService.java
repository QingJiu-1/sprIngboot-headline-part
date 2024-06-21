package com.QingJiu.service;

import com.QingJiu.pojo.Type;
import com.QingJiu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Qing_Jiu
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-06-04 20:21:45
*/
public interface TypeService extends IService<Type> {

    /**
     * 查询所有类别数据
     * @return
     */
    Result findAllTypes();
}
