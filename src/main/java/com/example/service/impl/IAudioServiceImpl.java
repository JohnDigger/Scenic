package com.example.service.impl;

import com.example.mapper.AudioMapper;
import com.example.mapper.ScenicMapper;
import com.example.model.Audio;
import com.example.model.JsonResult;
import com.example.service.IAudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 贾佳
 * @date 2021/8/22 15:11
 */
@Service
public class IAudioServiceImpl implements IAudioService {
    @Autowired(required = false)
    AudioMapper audioMapper;

    @Override
    public int insertAudio(Audio audio) {

        return audioMapper.InsertAudio(audio);
    }

    @Override
    public JsonResult getAll(int page,int limit) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("success");
        jsonResult.setData(audioMapper.getAllAudio(page,limit));
        jsonResult.setCount(audioMapper.countAll());
        return jsonResult;
    }

    @Override
    public JsonResult deleteById(int id) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("success");
        jsonResult.setData(audioMapper.deleteById(id));
        jsonResult.setCount(1);
        return jsonResult;
    }

    @Override
    public JsonResult updateAll(Audio audio) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("success");
        jsonResult.setData(audioMapper.updateById(audio));
        return jsonResult;
    }
}
