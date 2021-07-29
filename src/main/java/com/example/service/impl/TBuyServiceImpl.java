package com.example.service.impl;


import com.example.model.TBuy;
import com.example.service.TBuyService;
import com.example.mapper.TBuyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*
*/
@Service
public class TBuyServiceImpl implements TBuyService{

    @Autowired(required = false)
    TBuyMapper tBuyMapper;

    @Override
    public List<TBuy> getScenicBuyCount(String startTime, String endTime) {
        return tBuyMapper.getScenicBuyCount(startTime,endTime);
    }

    @Override
    public List<TBuy> getSevenCount() {
        return tBuyMapper.getSevenCount();
    }

    @Override
    public List<TBuy> getYearCount(String year) {
        return tBuyMapper.getYearCount(year);
    }

    @Override
    public int getOrderCount() {
        return tBuyMapper.getOrderCount();
    }

    @Override
    public int getUserCount() {
        return tBuyMapper.getUserCount();
    }

    @Override
    public int getAllUserCount() {
        return tBuyMapper.getAllUserCount();
    }

    @Override
    public int getScenicCount() {
        return tBuyMapper.getScenicCount();
    }

    @Override
    public List<TBuy> getOrder(int page, int limit) {
        return tBuyMapper.getAll(page,limit);
    }

    @Override
    public int getOderNum() {
        return tBuyMapper.OrderNum();
    }

    @Override
    public List<TBuy> searchOrder(String nick_name) {
        return tBuyMapper.searchAllByUserName(nick_name);
    }
}
