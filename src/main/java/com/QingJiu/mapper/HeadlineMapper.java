package com.QingJiu.mapper;

import com.QingJiu.pojo.Headline;
import com.QingJiu.pojo.vo.PortalVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author Qing_Jiu
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-06-04 20:21:45
* @Entity com.QingJiu.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    IPage<Map> selectMyPage(IPage page,@Param("portalVo") PortalVo portalVo);


    Map queryDatailMap(Integer hid);
}




