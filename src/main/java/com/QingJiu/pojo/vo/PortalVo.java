package com.QingJiu.pojo.vo;

import lombok.Data;

/**
 *
 */
@Data
public class PortalVo {

    private String keyWords; //搜索标题关键字

    private int type = 0; //新闻类型默认为0

    private int pageNum = 1; //页码数默认为1

    private int pageSize = 10; //页大小默认为10

}
