package com.example.service;

import com.example.model.TBuy;

import java.util.List;


/**
 *
 */
public interface TBuyService {


    List<TBuy> getScenicBuyCount(String startTime, String endTime);

    List<TBuy> getSevenCount();

    List<TBuy> getYearCount(String year);

    int getOrderCount();

    int getUserCount();

    int getAllUserCount();

    int getScenicCount();

    List<TBuy> getOrder(int page, int limit);

    int getOderNum();

    List<TBuy> searchOrder(String nick_name);
}
