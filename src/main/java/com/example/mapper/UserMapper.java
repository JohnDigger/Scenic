package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findByName(String name);
}
