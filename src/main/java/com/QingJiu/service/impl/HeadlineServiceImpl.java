package com.QingJiu.service.impl;

import com.QingJiu.pojo.vo.PortalVo;
import com.QingJiu.utils.JwtHelper;
import com.QingJiu.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.QingJiu.pojo.Headline;
import com.QingJiu.service.HeadlineService;
import com.QingJiu.mapper.HeadlineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author Qing_Jiu
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-06-04 20:21:45
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private HeadlineMapper headlineMapper;

    /**
     * 1、进行分页数据查询
     * 2、分页数据，拼接到result即可
     *
     * 注意1：查询需要自定义语句 自定义mapper的方法，携带分页
     * 注意2：返回的结果List<Map>
     * @param portalVo
     * @return
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {

        //page对象包含当前对象和页容量 我们给的都是空给其默认值分别为当前页1，页容量10
        //会导致一直加载第一页面
        /*IPage<Map> page = new Page<>();*/
        IPage<Map> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());

        headlineMapper.selectMyPage(page,portalVo);

        List<Map> records = page.getRecords();//将查询数据列表放入map集合当中

        Map map = new HashMap<>();
        map.put("pageData",records);
        map.put("pageNum",page.getCurrent());
        map.put("pageSize",page.getSize());
        map.put("totalPage",page.getPages());
        map.put("totalSize",page.getTotal());

        Map data = new HashMap<>();
        data.put("pageInfo",map);

        return Result.ok(data);
    }

    /**
     * 根据id查询详情
     *
     * 1、查询对应的数据接口【多表查询 头条和用户表，方法需要自定义 返回map即可】
     * 2、修改阅读量
     * @param hid
     * @return
     */

    @Override
    public Result showHeadlineDetail(Integer hid) {

        Map data = headlineMapper.queryDatailMap(hid);
        Map map = new HashMap<>();
        map.put("headline",data);

        //修改阅读量
        Headline headline = new Headline();
        headline.setHid((Integer) data.get("hid"));//获取id
        headline.setVersion((Integer) data.get("version"));//获取版本
        //阅读量+1
        headline.setPageViews((Integer) data.get("pageViews") + 1);//获取阅读量

        headlineMapper.updateById(headline);

        return Result.ok(map);
    }

    @Override
    public Result publish(Headline headline,String token) {

        //根据toke查询用户id
        int userId = jwtHelper.getUserId(token).intValue();
        //数据装配
        headline.setPublisher(userId);//用户id
        headline.setPageViews(0);//阅读量
        headline.setCreateTime(new Date());//当前时间
        headline.setUpdateTime(new Date());//修改时间

        //数据插入
        headlineMapper.insert(headline);

        return Result.ok(null);
    }

    /**
     * 1、hid查询数据的最新version
     * 2、修改数据的修改时间为当前为当前时间
     * @param headline
     * @return
     */
    @Override
    public Result updateData(Headline headline) {

        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version);//乐观锁效果
        headline.setUpdateTime(new Date());//当前时间

        headlineMapper.updateById(headline);

        return Result.ok(null);
    }
}




