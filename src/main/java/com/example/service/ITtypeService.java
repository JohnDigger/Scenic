package com.example.service;

import com.example.model.JsonResult;
import com.example.model.Ttype;

import java.util.List;

/**
 * @author 贾佳
 * @date 2021/9/25 20:18
 */
public interface ITtypeService {
    List<Ttype> getAllType();
    int addType(String type);
}
