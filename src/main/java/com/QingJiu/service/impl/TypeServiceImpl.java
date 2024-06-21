package com.QingJiu.service.impl;

import com.QingJiu.utils.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.QingJiu.pojo.Type;
import com.QingJiu.service.TypeService;
import com.QingJiu.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author Qing_Jiu
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-06-04 20:21:45
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Result findAllTypes() {
        List<Type> types = typeMapper.selectList(null);

        return Result.ok(types);
    }
}




