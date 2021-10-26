package com.example.service.impl;

import com.example.mapper.TImgMapper;
import com.example.model.TImg;
import com.example.service.IImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 贾佳
 * @date 2021/10/25 22:50
 */
@Service
public class IImgServiceimpl implements IImgService {
    @Autowired
    TImgMapper tImgMapper;
    @Override
    public Map CallBack(TImg tImg) {
        Map map = new HashMap<String,String>();
        if (tImgMapper.insert(tImg)==1){
            map.put("error",0);
            map.put("url",tImg.getImgPath());
        }else {
            map.put("error",1);
            map.put("message","错误信息");
        }
        return map;
    }
}
