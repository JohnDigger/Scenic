package com.example.controller;


import com.example.model.Audio;
import com.example.model.JsonResult;
import com.example.service.IAudioService;
import com.example.service.IScenicService;
import com.example.util.FileNameUtil;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class AudioController {
    @Autowired
    IScenicService scenicService;

    @Autowired
    IAudioService iAudioService;

    @RequestMapping(value = {"/uploadAudio"}, method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult uploadAudio(@Validated @RequestParam(value = "scenicName", required = true) String scenicName, @Validated @RequestParam(value = "audioName", required = true) String audioName, @Validated @RequestParam(value = "audioMoney", required = true) String audioMoney, @RequestParam("attachs") MultipartFile[] files) {
        JsonResult jsonResult = new JsonResult();
        Audio audio = new Audio();
        audio.setAudioMoney(audioMoney).setAudioName(audioName).setScenicId(this.scenicService.getInfoByName(scenicName)).setScenicName(scenicName);
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                jsonResult.setCode(Integer.valueOf(1));
                jsonResult.setMsg("file null");
                jsonResult.setData("文件为空");
            }
            String fileName = ((String)Objects.<String>requireNonNull(file.getOriginalFilename())).replace(" ", "");
            fileName = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + "_" + fileName;
            fileName = FileNameUtil.getFileName(fileName);
            System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: " + fileName + "\n");

            String realpath = "C:/scenic/audio/";
            String path = realpath + fileName;
//            System.out.print("+ path + "\n");
            File dest = new File(path);
            if (dest.exists()) {
                jsonResult.setCode(Integer.valueOf(2));
                jsonResult.setMsg("File exist");
                jsonResult.setData("文件已存在");
                return jsonResult;
            }
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
                jsonResult.setCode(Integer.valueOf(4));
                jsonResult.setMsg("parent file not exist");
                jsonResult.setData("父文件不存在");
            }
            try {
                file.transferTo(dest);
                System.out.print("保存文件路径" + path + "\n");
                String url = "http://119.23.61.114:80/audio/" + fileName;
                audio.setAudioUrl(url);
                this.iAudioService.insertAudio(audio);
                jsonResult.setCode(Integer.valueOf(0));
                jsonResult.setMsg("sucess");
                jsonResult.setData("上传成功");
            } catch (IOException e) {
                jsonResult.setCode(Integer.valueOf(3));
                jsonResult.setMsg("failed");
                jsonResult.setData("上传失败");
            }
        }
        return jsonResult;
    }

    @RequestMapping(value = {"getAudio"}, method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult audioAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        return this.iAudioService.getAll(page, limit);
    }

    @RequestMapping({"deleteAudio"})
    @ResponseBody
    public JsonResult audioDelete(@RequestParam("id") int id) {
        return this.iAudioService.deleteById(id);
    }

    @RequestMapping({"updateAudio"})
    @ResponseBody
    public JsonResult audioUpdate(@Validated Audio audio, @RequestParam(value = "attachs", required = false) MultipartFile[] files) {
        JsonResult jsonResult = new JsonResult();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                this.iAudioService.updateAll(audio);
                jsonResult.setCode(Integer.valueOf(1));
                jsonResult.setMsg("file null");
                jsonResult.setData("文件为空");
            }
            String fileName = ((String)Objects.<String>requireNonNull(file.getOriginalFilename())).replace(" ", "");
            fileName = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + "_" + fileName;
            fileName = FileNameUtil.getFileName(fileName);
            System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: " + fileName + "\n");
            String realpath = "C:/scenic/audio/";
            String path = realpath + fileName;
            System.out.print("保存文件绝对路径" + path + "\n");
                    File dest = new File(path);
            if (dest.exists()) {
                jsonResult.setCode(Integer.valueOf(2));
                jsonResult.setMsg("File exist");
                jsonResult.setData("文件已存在");
                return jsonResult;
            }
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
                jsonResult.setCode(Integer.valueOf(4));
                jsonResult.setMsg("parent file not exist");
                jsonResult.setData("父文件不存在");
            }
            try {
                file.transferTo(dest);
                System.out.print("保存文件路径" + path + "\n");
                String url = "http://119.23.61.114:80/audio/" + fileName;
                audio.setAudioUrl(url);
                jsonResult = this.iAudioService.updateAll(audio);
            } catch (IOException e) {
                jsonResult.setCode(Integer.valueOf(3));
                jsonResult.setMsg("failed");
                jsonResult.setData("上传失败");
            }
        }
        return jsonResult;
    }
}
