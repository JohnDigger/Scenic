package com.example.service.impl;

import com.example.mapper.TtypeMapper;
import com.example.model.JsonResult;
import com.example.model.Ttype;
import com.example.service.ITtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 贾佳
 * @date 2021/9/25 20:19
 */
@Service
public class ITtypeServiceImpl implements ITtypeService {
    @Autowired(required = false)
    TtypeMapper ttypeMapper;
    @Override
    public List<Ttype> getAllType() {
        return ttypeMapper.getType();
    }

    @Override
    public int addType(String type) {
        Ttype ttype = new Ttype();
        ttype.setScenicType(type);
        return ttypeMapper.insert(ttype);
    }
}
