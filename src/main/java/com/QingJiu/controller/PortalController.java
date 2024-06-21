package com.QingJiu.controller;

import com.QingJiu.pojo.Type;
import com.QingJiu.pojo.vo.PortalVo;
import com.QingJiu.service.HeadlineService;
import com.QingJiu.service.TypeService;
import com.QingJiu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("portal")
@RestController
public class PortalController {

    @Autowired
    private TypeService typeService; //类的业务

    @Autowired
    private HeadlineService headlineService; //头条业务

    @GetMapping("findAllTypes")
    public Result findAllTypes(){
       Result result = typeService.findAllTypes();
       return result;
    }

    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){//接收传入的json数据
       Result result = headlineService.findNewsPage(portalVo);
       return  result;
    }

    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }

}
