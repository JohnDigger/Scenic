package com.example.service.impl;
import com.example.model.*;
import com.example.mapper.ScenicMapper;
import com.example.service.IScenicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2021-04-01
 */
@Service
public class ScenicServiceImpl extends ServiceImpl<ScenicMapper, Scenic> implements IScenicService {

    @Autowired(required = false)
    ScenicMapper scenicMapper;


    @Override
    public Map<Integer, Object> getList() {
        List<Scenic> lis = scenicMapper.getList();
        Map<Integer,Object> mp = new HashMap<>();

        for (Scenic i:lis){
            Integer id = i.getId();
            mp.put(id,i);
        }
        return mp;
    }

    @Override
    public List<Scenic> getByIdPlus(Integer id) {
        return scenicMapper.getByIdPlus(id);
    }

    @Override
    public List<Audio> getMoney(Integer scenicId) {
        return scenicMapper.getMoney(scenicId);
    }

    @Override
    public Scenic getAudio(Integer audioId) {
        return scenicMapper.getAudio(audioId);
    }

    @Override
    public int saveUserBuyMsg(Integer audioId, String openId) {
        return scenicMapper.saveUserBuyMsg(audioId,openId);
    }

    @Override
    public List<TBuy> getUserBuyMsg(String openId) {
        return scenicMapper.getUserBuyMsg(openId);
    }

    @Override
    public int saveUserMsg(String nickName, String openId) {
        TUser tUser1 = scenicMapper.getUserByOpenId(openId);
        if (tUser1 == null){
            scenicMapper.saveUserMsg(nickName,openId);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Scenic> searchScenic(String Sname) {
        return scenicMapper.searchScenic(Sname);
    }

    @Override
    public List<Scenic> getScenicByType(String typeId) {
        return scenicMapper.getScenicByType(typeId);
    }

    @Override
    public List<Scenic> getPage(int start, int num) {
        return scenicMapper.getPage(start-1,num);
    }






    @Override
    public JsonResult updateVideo(String name, String video_path) {
        JsonResult jsonResult = new JsonResult();
        int updateStatus = scenicMapper.updateVideo_pathInt(name,video_path);
        if (updateStatus == 0){
            jsonResult.setCode(0);
            jsonResult.setMsg("上传失败");
            jsonResult.setData("上传失败，请检查是否不存在此景区或者景区名为空");
        }else if (updateStatus == 1){
            jsonResult.setCode(1);
            jsonResult.setMsg("sucess");
            jsonResult.setData("上传成功");
        }
        return jsonResult;
    }



    @Override
    public JsonResult queryMsg(int page,int limit) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("success");
        jsonResult.setData(scenicMapper.queryMsg(page,limit));
        jsonResult.setCount(scenicMapper.queryMsg(page,limit).size());
        return jsonResult;
    }

    @Override
    public int insertMsg(Scenic scenic) {
        return scenicMapper.insertMsg(scenic);
    }

    @Override
    public JsonResult deleteMsg(Integer id) {
        JsonResult jr = new JsonResult();
        jr.setCode(0);
        jr.setMsg("success");
        jr.setData(scenicMapper.deleteById(id));
        return jr;
    }

    @Override
    public JsonResult search(String name) {
        JsonResult jr = new JsonResult();
        jr.setCode(0);
        jr.setMsg("success");
        jr.setData(scenicMapper.searchAllByName(name));
        return jr;
    }

    @Override
    public int updateMsg(Scenic scenic) {
        return scenicMapper.updateById(scenic);
    }



}
