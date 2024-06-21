package com.QingJiu.service;

import com.QingJiu.pojo.Headline;
import com.QingJiu.pojo.vo.PortalVo;
import com.QingJiu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Qing_Jiu
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-06-04 20:21:45
*/
public interface HeadlineService extends IService<Headline> {

    /**
     * 首页数据查询
     * @param portalVo
     * @return
     */
    Result findNewsPage(PortalVo portalVo);

    /**
     * 根据id查询详情
     * @param hid
     * @return
     */
    Result showHeadlineDetail(Integer hid);

    /**
     * 发布头条的方法
     * @param headline
     * @return
     */
    Result publish(Headline headline,String token);

    /**
     * 修改头条实现
     * @param headline
     * @return
     */
    Result updateData(Headline headline);
}
