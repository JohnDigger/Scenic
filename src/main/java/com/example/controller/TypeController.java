package com.example.controller;

import com.example.model.JsonResult;
import com.example.service.impl.ITtypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 贾佳
 * @date 2021/9/25 20:13
 */
@Controller
public class TypeController {
    @Autowired
    ITtypeServiceImpl ttypeService;

    @RequestMapping("/addType")
    @ResponseBody
    public JsonResult addType(@RequestParam(value = "type",required = true) String type){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("添加成功");
        jsonResult.setData(ttypeService.addType(type));
        return jsonResult;
    }

    @RequestMapping("/queryAllType")
    @ResponseBody
    public JsonResult getAllType(){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("请求成功");
        jsonResult.setData(ttypeService.getAllType());
        return jsonResult;
    }


}
