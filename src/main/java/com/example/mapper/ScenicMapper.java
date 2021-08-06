package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.Audio;
import com.example.model.Scenic;
import com.example.model.TBuy;
import com.example.model.TUser;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2021-04-01
 */
@Mapper
public interface ScenicMapper extends BaseMapper<Scenic> {

    List<Scenic> getByIdPlus(Integer id);

    List<Scenic> queryMsg();

    List<Scenic> searchAllByName(String name);

    List<Scenic> findAll();

    int insertMsg(Scenic scenic);

    List<Audio> getMoney(Integer scenicId);

    Scenic getAudio(Integer scenicId);

    int updatePicById(int id,String picture_path);

    int updateByName(String name,String picture_path);

    int updateAudioPathById(int id,String audio_path);

    int updateAudioPathByName(String name,String audio_path);

    int updateVideo_pathInt(String name,String video_path);


    Scenic findByNameScenic(String name);

    int InsertScenic(Scenic scenic);

    int saveUserBuyMsg(Integer audioId,String openId);

    List<TBuy> getUserBuyMsg(String openId);

    TUser getUserByOpenId(String openId);

    void saveUserMsg(String nickName, String openId);
}
