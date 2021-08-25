package com.example.mapper;

import com.example.model.Audio;
import com.example.model.JsonResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 贾佳
 * @date 2021/8/22 14:51
 */
@Mapper
public interface AudioMapper {
    int InsertAudio(Audio audio);

    List<Audio> getAllAudio(int page,int limit);

    int countAll();

    int deleteById(int id);

    int updateById(Audio audio);
}
