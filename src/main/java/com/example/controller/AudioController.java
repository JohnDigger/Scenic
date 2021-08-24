package com.example.controller;


import com.example.model.Audio;
import com.example.model.JsonResult;
import com.example.service.IAudioService;
import com.example.service.IScenicService;
import com.example.util.FileNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Controller
@CrossOrigin(origins = "*",maxAge = 3600)
public class AudioController {
    @Autowired
    IScenicService scenicService;

    @Autowired
    IAudioService iAudioService;

    @RequestMapping(value="/uploadAudio",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadAudio(@Validated @RequestParam(value = "scenicName",required = true) String scenicName,
                                  @Validated @RequestParam(value = "audioName",required = true) String audioName,
                                  @Validated @RequestParam(value = "audioMoney",required = true) String audioMoney,
                                  @RequestParam("attachs") MultipartFile[] files) {


        JsonResult jsonResult = new JsonResult();


        Audio audio = new Audio();
        audio.setAudioMoney(audioMoney).setAudioName(audioName).setScenicId(scenicService.getInfoByName(scenicName)).setScenicName(scenicName);

        for (MultipartFile file : files){
            //判断文件是否为空
            if (file.isEmpty()) {
                jsonResult.setCode(1);
                jsonResult.setMsg("file null");
                jsonResult.setData("文件为空");

            }

            // 获取文件名
            String fileName = Objects.requireNonNull(file.getOriginalFilename()).replace(" ","");
            fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
            fileName = FileNameUtil.getFileName(fileName);
            System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: "+fileName+"\n");
            //获取当前工程文件路径
//            String realpath = this.getClass().getResource("/static/upload/").getPath();
            String realpath = "C:/scenic/audio/";
            //加个时间戳，尽量避免文件名称重复
            String path = realpath + fileName;
            //文件绝对路径
            System.out.print("保存文件绝对路径"+path+"\n");

            //创建文件路径
            File dest = new File(path);

            //判断文件是否已经存在
            if (dest.exists()) {
                jsonResult.setCode(2);
                jsonResult.setMsg("File exist");
                jsonResult.setData("文件已存在");
                return jsonResult;
            }

            //判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
                jsonResult.setCode(4);
                jsonResult.setMsg("parent file not exist");
                jsonResult.setData("父文件不存在");
            }

            String url;
            try {
                //上传文件
                file.transferTo(dest); //保存文件
                System.out.print("保存文件路径"+path+"\n");
                url="http://119.23.61.114:80/audio/"+fileName;
                audio.setAudioUrl(url);
                iAudioService.insertAudio(audio);
                jsonResult.setCode(0);
                jsonResult.setMsg("sucess");
                jsonResult.setData("上传成功");

            } catch (IOException e) {
                jsonResult.setCode(3);
                jsonResult.setMsg("failed");
                jsonResult.setData("上传失败");

            }

        }

//        System.out.print("上传的文件名为: "+fileName+"\n");


        return jsonResult;
    }

    @RequestMapping(value = "getAudio",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult audioAll(@RequestParam(value = "page",required = false,defaultValue = "1")int page,@RequestParam(value = "limit",required = false,defaultValue = "10")int limit){
      
        return iAudioService.getAll(page,limit);
    }

    @RequestMapping(value = "deleteAudio")
    @ResponseBody
    public JsonResult audioDelete(@RequestParam("id") int id){
        return iAudioService.deleteById(id);
    }

    @RequestMapping(value = "updateAudio")
    @ResponseBody
    public JsonResult audioUpdate(@Validated Audio audio,@RequestParam(value = "attachs",required = false) MultipartFile[] files ){

        JsonResult jsonResult = new JsonResult();


        for (MultipartFile file : files){
            //判断文件是否为空
            if (file.isEmpty()) {
                iAudioService.updateAll(audio);
                jsonResult.setCode(1);
                jsonResult.setMsg("file null");
                jsonResult.setData("文件为空");

            }

            // 获取文件名
            String fileName = Objects.requireNonNull(file.getOriginalFilename()).replace(" ","");
            fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
            fileName = FileNameUtil.getFileName(fileName);
            System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: "+fileName+"\n");
            //获取当前工程文件路径
//            String realpath = this.getClass().getResource("/static/upload/").getPath();
            String realpath = "C:/scenic/audio/";
            //加个时间戳，尽量避免文件名称重复
            String path = realpath + fileName;
            //文件绝对路径
            System.out.print("保存文件绝对路径"+path+"\n");

            //创建文件路径
            File dest = new File(path);

            //判断文件是否已经存在
            if (dest.exists()) {
                jsonResult.setCode(2);
                jsonResult.setMsg("File exist");
                jsonResult.setData("文件已存在");
                return jsonResult;
            }

            //判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
                jsonResult.setCode(4);
                jsonResult.setMsg("parent file not exist");
                jsonResult.setData("父文件不存在");
            }

            String url;
            try {
                //上传文件
                file.transferTo(dest); //保存文件
                System.out.print("保存文件路径"+path+"\n");
                url="http://119.23.61.114:80/audio/"+fileName;
                audio.setAudioUrl(url);
                jsonResult = iAudioService.updateAll(audio);


            } catch (IOException e) {
                jsonResult.setCode(3);
                jsonResult.setMsg("failed");
                jsonResult.setData("上传失败");

            }

        }

//        System.out.print("上传的文件名为: "+fileName+"\n");


        return jsonResult;
    }
}
