package com.example.controller;



import com.example.mapper.ScenicMapper;
import com.example.model.*;
import com.example.service.IImgService;
import com.example.service.IScenicService;
import com.example.util.FileNameUtil;
import com.example.util.PicUploadUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = {"/scenic"}, produces = {"application/json; charset=utf-8"})
@ResponseBody
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class ScenicController {
    protected static final Logger logger = LoggerFactory.getLogger(com.example.controller.ScenicController.class);

    @Autowired
    private IScenicService scenicService;

    @Autowired(required = false)
    private ScenicMapper scenicMapper;

    @Autowired
    private IImgService iImgService;

    @RequestMapping({"/getScenic"})
    public Scenic getScenic(int id) {
        return (Scenic)this.scenicService.getById(Integer.valueOf(id));
    }

    @RequestMapping({"/getList"})
    public Map<Integer, Object> getList() {
        return this.scenicService.getList();
    }

    @RequestMapping({"/getByIdPlus"})
    public List<Scenic> getByIdPlus(Integer id) {
        return this.scenicService.getByIdPlus(id);
    }

    @RequestMapping({"/getMoney"})
    public List<Audio> getMoney(Integer scenicId) {
        return this.scenicService.getMoney(scenicId);
    }

    @RequestMapping({"/getAudio"})
    public Scenic getAudio(Integer audioId) {
        return this.scenicService.getAudio(audioId);
    }

    @RequestMapping({"/saveUserBuyMsg"})
    public int saveUserBuyMsg(Integer audioId, String openId) {
        return this.scenicService.saveUserBuyMsg(audioId, openId);
    }

    @RequestMapping({"/saveUserMsg"})
    public int saveUserMsg(String nickName, String openId) {
        return this.scenicService.saveUserMsg(nickName, openId);
    }

    @RequestMapping({"/getUserBuyMsg"})
    public List<TBuy> getUserBuyMsg(String openId) {
        return this.scenicService.getUserBuyMsg(openId);
    }

    @RequestMapping({"/getScenicByType"})
    public List<Scenic> getScenicByType(String scenicType) {
        return this.scenicService.getScenicByType(scenicType);
    }

    @RequestMapping({"/search"})
    public List<Scenic> searchScenic(String Sname) {
        return this.scenicService.searchScenic(Sname);
    }

    @RequestMapping({"/getPage"})
    public List<Scenic> getPage(int start, int num) {
        return this.scenicService.getPage(start, num);
    }

    @RequestMapping(value = {"/queryMsg"}, method = {RequestMethod.POST})
    public JsonResult queryMsg(@RequestParam(name = "page", required = false, defaultValue = "0") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        return scenicService.queryMsg(page, limit);
    }

    @RequestMapping({"/insertMsg"})
    public int insertMsg(Scenic scenic) {
        return scenicService.insertMsg(scenic);
    }

    @RequestMapping(value = {"/deleteMsg"}, produces = {"application/json"})
    public JsonResult deleteMsg(@RequestParam(value = "id", required = true) Integer id) {
        System.out.println(this.scenicService.deleteMsg(id));
        return this.scenicService.deleteMsg(id);
    }

    @RequestMapping({"/updateMsg"})
    public JsonResult updateMsgMsg(Scenic scenic) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(Integer.valueOf(0));
        jsonResult.setMsg("更新成功");
        jsonResult.setData(Integer.valueOf(this.scenicService.updateMsg(scenic)));
        return jsonResult;
    }

    @RequestMapping({"/searchByName"})
    public JsonResult search(@RequestParam("name") String name) {
        return this.scenicService.search(name);
    }

    @RequestMapping({"insertText"})
    @ResponseBody
    public JsonResult textInsert(@RequestParam String name, @RequestParam String list, @RequestParam String text, @RequestParam String scenicType) {
        System.out.println(name);
        JsonResult jsonResult = new JsonResult();
        Scenic scenic = new Scenic();
        scenic.setName(name);
        scenic.setList(list);
        scenic.setText(text);
        scenic.setScenicType(scenicType);
        try {
            this.scenicMapper.InsertScenic(scenic);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setCode(Integer.valueOf(5));
            jsonResult.setMsg("name重复");
            jsonResult.setData("name已存在");
            return jsonResult;
        }
        jsonResult.setCode(Integer.valueOf(0));
        jsonResult.setMsg("success");
        jsonResult.setData("提交成功");
        return jsonResult;
    }

    @RequestMapping(value = {"addInfo"}, method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult add(@Validated @RequestParam(value = "name", required = true) String name, @RequestParam("attachs") MultipartFile[] attachs, HttpServletResponse response, HttpServletRequest req) throws IOException {
        JsonResult jsonResult = new JsonResult();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Cache-Control", "no-cache");
        Scenic scenic = new Scenic();
        String realpath = "C:/scenic/picture/";
        for (MultipartFile attach : attachs) {
            if (!attach.isEmpty()) {
                BufferedImage bi = ImageIO.read(attach.getInputStream());
                if (bi == null) {
                    jsonResult.setCode(Integer.valueOf(2));
                    jsonResult.setMsg("not image");
                    jsonResult.setData("上传的图片为空");
                    return jsonResult;
                }
                String picName = PicUploadUtil.picRename(Objects.<String>requireNonNull(attach.getContentType()));
                String path = realpath + "/" + picName;
                File f = new File(path);
                scenic.setPicturePath("http://119.23.61.114:80/picture/" + picName);
                this.scenicMapper.updateByName(name, "http://119.23.61.114:80/picture/" + picName);
                System.out.println(name);
                try {
                    FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        jsonResult.setCode(Integer.valueOf(0));
        jsonResult.setMsg("success");
        jsonResult.setData(realpath);
        return jsonResult;
    }

    @RequestMapping({"/uploadVideo"})
    @ResponseBody
    public JsonResult uploadFile(@RequestParam("attachs") MultipartFile[] files, @Validated @RequestParam(value = "name", required = true) String name) {
        JsonResult jsonResult = new JsonResult();
        System.out.print("\n");
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                jsonResult.setCode(Integer.valueOf(1));
                jsonResult.setMsg("file null");
                jsonResult.setData("文件为空");
            }
            String fileName = ((String)Objects.<String>requireNonNull(file.getOriginalFilename())).replace(" ", "");
            fileName = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + "_" + fileName;
            fileName = FileNameUtil.getFileName(fileName);
            System.out.print("" + fileName + "\n");
            String realpath = "C:/scenic/video/";
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
                String url = "http://119.23.61.114:80/video/" + fileName;
                return this.scenicService.updateVideo(name, url);
            } catch (IOException e) {
                jsonResult.setCode(Integer.valueOf(3));
                jsonResult.setMsg("failed");
                jsonResult.setData("上传失败");
            }
        }
        return jsonResult;
    }
    @RequestMapping({"/editor_upload"})
    @ResponseBody
    public Map editorUpload(@RequestParam("imgFile") MultipartFile[] files) {
        JsonResult jsonResult = new JsonResult();
        Map<String,String> map = new HashMap<>();
        TImg img = new TImg();
        System.out.print("\n");
        for (MultipartFile file : files) {
            if (file.isEmpty()) {

                map.put("error","1");
                map.put("message","错误信息");
            }
            String fileName = ((String)Objects.<String>requireNonNull(file.getOriginalFilename())).replace(" ", "");
            fileName = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + "_" + fileName;
            fileName = FileNameUtil.getFileName(fileName);

            String realpath = "C:/scenic/picture/";
            String path = realpath + fileName;
            System.out.print("保存文件绝对路径" + path + "\n");
            File dest = new File(path);
            if (dest.exists()) {
                map.put("message","错误信息");

            }
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
                map.put("message","错误信息");
            }
            try {

                file.transferTo(dest);
                System.out.print("保存文件路径" + path + "\n");
                String url = "http://119.23.61.114:80/picture/" + fileName;
                img.setImgPath(url);
                return iImgService.CallBack(img);
            } catch (IOException e) {
                map.put("message","错误信息");
            }
        }
        return map;
    }
}
