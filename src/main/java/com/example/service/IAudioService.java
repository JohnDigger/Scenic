package com.example.service;

import com.example.model.Audio;
import com.example.model.JsonResult;

/**
 * @author 贾佳
 * @date 2021/8/22 15:09
 */
public interface IAudioService {
    int insertAudio(Audio audio);
    JsonResult getAll(int page,int limit);
    JsonResult deleteById(int id);

    JsonResult updateAll(Audio audio);
}
