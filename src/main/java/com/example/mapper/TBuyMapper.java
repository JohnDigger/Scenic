package com.example.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.TBuy;
import java.util.List;

public interface TBuyMapper extends BaseMapper<TBuy> {

    List<TBuy> getScenicBuyCount(String startTime, String endTime);

    List<TBuy> getSevenCount();

    List<TBuy> getYearCount(String year);

    int getOrderCount();

    int getUserCount();

    int getAllUserCount();

    int getScenicCount();

    int OrderNum();

    List<TBuy> getAll(int page,int limit);

    List<TBuy> searchAllByUserName(String nick_name);
}
