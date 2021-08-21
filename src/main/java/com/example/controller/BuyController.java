package com.example.controller;


import com.example.model.JsonResult;
import com.example.model.TBuy;
import com.example.service.TBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author astupidcoder
 * @date 2021/7/24 23:41
 */
@Controller
@ResponseBody
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping(produces = "application/json; charset=utf-8")
public class BuyController {

    @Autowired
    TBuyService service;

    @RequestMapping("getSevenCount")
    public List<TBuy> getSevenCount(){
        return service.getSevenCount();
    }

    @RequestMapping("getYearCount")
    public JsonResult getYearCount(@RequestParam(value = "year",required = true) String year){
        System.out.println(year);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("sucess");
        jsonResult.setData(service.getYearCount(year));
        return jsonResult;
    }

    @RequestMapping("getScenicBuyCount")
    public List<TBuy> getScenicBuyCount(String startTime,String endTime){
        return service.getScenicBuyCount(startTime,endTime);
    }

    @RequestMapping("getOrderCount")
    public int getOrderCount(){
        return service.getOrderCount();
    }

    @RequestMapping("getUserCount")
    public int getUserCount(){
        return service.getUserCount();
    }

    @RequestMapping("getAllUserCount")
    public int getAllUserCount(){
        return service.getAllUserCount();
    }

    @RequestMapping("getScenicCount")
    public int getScenicCount(){
        return service.getScenicCount();
    }

    @RequestMapping(value = "queryOrder",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getOrder(@RequestParam(name = "page",required = false,defaultValue = "1") int page,@RequestParam(name = "limit",required = false,defaultValue = "10") int limit){
        JsonResult jr = new JsonResult();
        jr.setCode(0);
        jr.setMsg("success");
        jr.setCount(service.getOderNum());
//        jr.setCount(10);
        jr.setData(service.getOrder(page,limit));
        return jr;
    }

    @RequestMapping(value = "searchOrder",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult searchOrder(@RequestParam("nick_name") String nick_name){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("success");
        jsonResult.setCount(service.searchOrder("%"+nick_name+"%").size());
        jsonResult.setData(service.searchOrder("%"+nick_name+"%"));
        return jsonResult;
    }

}
