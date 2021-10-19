package com.example.controller;


import com.example.model.JsonResult;
import com.example.model.TBuy;
import com.example.service.TBuyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
@RequestMapping(produces = {"application/json; charset=utf-8"})
public class BuyController {
    @Autowired
    TBuyService service;

    @RequestMapping({"getSevenCount"})
    public List<TBuy> getSevenCount() {
        return this.service.getSevenCount();
    }


    @RequestMapping({"getYearCount"})
    public JsonResult getYearCount(@RequestParam(value = "year", required = true) String year) {
        System.out.println(year);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(Integer.valueOf(0));
        jsonResult.setMsg("sucess");
        jsonResult.setData(this.service.getYearCount(year));
        return jsonResult;
    }

    @RequestMapping({"getScenicBuyCount"})
    public List<TBuy> getScenicBuyCount(String startTime, String endTime) {
        return this.service.getScenicBuyCount(startTime, endTime);
    }

    @RequestMapping({"getOrderCount"})
    public int getOrderCount() {
        return this.service.getOrderCount();
    }

    @RequestMapping({"getUserCount"})
    public int getUserCount() {
        return this.service.getUserCount();
    }

    @RequestMapping({"getAllUserCount"})
    public int getAllUserCount() {
        return this.service.getAllUserCount();
    }

    @RequestMapping({"getScenicCount"})
    public int getScenicCount() {
        return this.service.getScenicCount();
    }

    @RequestMapping(value = {"queryOrder"}, method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult getOrder(@RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        JsonResult jr = new JsonResult();
        jr.setCode(Integer.valueOf(0));
        jr.setMsg("success");
        jr.setCount(this.service.getOderNum());
        jr.setData(this.service.getOrder(page, limit));
        return jr;
    }

    @RequestMapping(value = {"searchOrder"}, method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult searchOrder(@RequestParam("nick_name") String nick_name) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(Integer.valueOf(0));
        jsonResult.setMsg("success");
        jsonResult.setCount(this.service.searchOrder("%" + nick_name + "%").size());
        jsonResult.setData(this.service.searchOrder("%" + nick_name + "%"));
        return jsonResult;
    }
}
