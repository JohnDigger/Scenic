package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.TImg;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 贾佳
 * @date 2021/10/25 22:42
 */
@Mapper
public interface TImgMapper extends BaseMapper<TImg> {
    @Override
    int insert(TImg entity);
}
