package com.example.service;

import com.example.model.Audio;
import com.example.model.Scenic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.JsonResult;
import com.example.model.TBuy;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2021-04-01
 */
public interface IScenicService extends IService<Scenic> {
    Map<Integer, Object> getList();

    List<Scenic> getByIdPlus(Integer id);

    JsonResult queryMsg(int page,int limit);

    int insertMsg(Scenic scenic);

    JsonResult deleteMsg(Integer id);

    JsonResult search(String name);

    int updateMsg(Scenic scenic);

    List<Audio> getMoney(Integer scenicId);

    Scenic getAudio(Integer audioId);

    int saveUserBuyMsg(Integer audioId, String openId);

    List<TBuy> getUserBuyMsg(String username);

    int saveUserMsg(String nickName, String openId);

    List<Scenic> getScenicByType(String scenicType);

    JsonResult updateVideo(String name,String video_path);

    List<Scenic> searchScenic(String Sname);

    List<Scenic> getPage(int start, int num);

    int getInfoByName(String name);
}
