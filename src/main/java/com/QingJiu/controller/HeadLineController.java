package com.QingJiu.controller;

import com.QingJiu.pojo.Headline;
import com.QingJiu.service.HeadlineService;
import com.QingJiu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("headline")
public class HeadLineController {
    @Autowired
    private HeadlineService headlineService;

    //登录以后才可以访问
    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline,@RequestHeader String token){
        Result result = headlineService.publish(headline,token);
        return result;
    }

    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid){
        Headline byId = headlineService.getById(hid);
        Map map = new HashMap<>();
        map.put("headline",byId);

        return Result.ok(map);
    }

    @PostMapping("update")
    public Result update(@RequestBody Headline headline){
        Result result = headlineService.updateData(headline);
        return  result;
    }

    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid){

        headlineService.removeById(hid);

        return Result.ok(null);

    }

}
