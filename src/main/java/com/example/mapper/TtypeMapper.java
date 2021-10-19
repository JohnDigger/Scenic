package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.Ttype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 贾佳
 * @date 2021/9/25 19:56
 */
@Mapper
public interface TtypeMapper extends BaseMapper<Ttype> {
    List<Ttype> getType();
}
