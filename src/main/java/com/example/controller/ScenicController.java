package com.example.controller;

import ch.qos.logback.core.util.FileUtil;
import com.example.mapper.ScenicMapper;
import com.example.model.*;
import com.example.service.IScenicService;
import com.example.model.JsonResult;
import com.example.util.PicUploadUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2021-04-01
 */
@Controller
@RequestMapping(value = "/scenic",produces = "application/json; charset=utf-8")
@ResponseBody
@CrossOrigin(origins = "*",maxAge = 3600)
public class ScenicController {
    //配置日志输出
    protected static final Logger logger = LoggerFactory.getLogger(ScenicController.class);


    @Autowired
    private IScenicService scenicService;

    @Autowired
    private ScenicMapper scenicMapper;

    //微信小程序
    @RequestMapping("/getScenic")
    public Scenic getScenic(int id){
        return scenicService.getById(id);
    }

    @RequestMapping("/getList")
    public Map<Integer, Object> getList(){
        return scenicService.getList();
    }

    @RequestMapping("/getByIdPlus")
    public List<Scenic> getByIdPlus(Integer id) {
        return scenicService.getByIdPlus(id);
    }

    @RequestMapping("/getMoney")
    public List<Audio> getMoney(Integer scenicId) {
        return scenicService.getMoney(scenicId);
    }

    @RequestMapping("/getAudio")
    public Scenic getAudio(Integer audioId) {
        return scenicService.getAudio(audioId);
    }

    @RequestMapping("/saveUserBuyMsg")
    public int saveUserBuyMsg(Integer audioId,String openId) {
        return scenicService.saveUserBuyMsg(audioId,openId);
    }

    @RequestMapping("/saveUserMsg")
    public int saveUserMsg(String nickName,String openId) {
        return scenicService.saveUserMsg(nickName,openId);
    }

    @RequestMapping("/getUserBuyMsg")
    public List<TBuy> getUserBuyMsg(String openId) {
        return scenicService.getUserBuyMsg(openId);
    }




    //后台管理
    @RequestMapping("/queryMsg")
    public JsonResult queryMsg(){
        return scenicService.queryMsg();
    }

    @RequestMapping("/insertMsg")
    public int insertMsg(Scenic scenic) {
        return scenicService.insertMsg(scenic);
    }

    @RequestMapping(value = "/deleteMsg",produces = "application/json")
    public JsonResult deleteMsg(@RequestParam(value = "id",required = true) Integer id) {
        System.out.println(scenicService.deleteMsg(id));
        return scenicService.deleteMsg(id);
    }

    //    @RequestMapping("/updateMsg")
//    public JsonResult updateMsgMsg(@RequestParam("id") int id,@RequestParam("name") String name,@RequestParam("list") String list,
//                                    @RequestParam("picturePath") String picturePath,@RequestParam("audioPath") String audioPath,@RequestParam("text") String text) {
//        JsonResult jsonResult = new JsonResult();
//        Scenic scenic = scenicMapper.selectById(id);
//        scenic.setName(name).setList(list).setPicturePath(picturePath).setAudioPath(audioPath).setText(text);
//
//        jsonResult.setCode(0);
//        jsonResult.setMsg("更新成功");
//        jsonResult.setData(scenicService.updateMsg(scenic));
//        return jsonResult;
//    }
    @RequestMapping("/updateMsg")
    public JsonResult updateMsgMsg(Scenic scenic) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("更新成功");
        jsonResult.setData(scenicService.updateMsg(scenic));
        return jsonResult;
    }


    @RequestMapping("/searchByName")
    public JsonResult search(@RequestParam("name") String name){
        return scenicService.search(name);
    }



    @RequestMapping("insertText")
    @ResponseBody
    public JsonResult textInsert(@RequestParam String name,@RequestParam String list,@RequestParam String text){
        JsonResult jsonResult = new JsonResult();
        Scenic scenic = new Scenic();
        scenic.setName(name);
        scenic.setList(list);
        scenic.setText(text);
        try {

            scenicMapper.InsertScenic(scenic);
        }catch (Exception e){
            jsonResult.setCode(5);
            jsonResult.setMsg("name重复");
            jsonResult.setData("name已存在");
            return jsonResult;
        }
        jsonResult.setCode(0);
        jsonResult.setMsg("success");
        jsonResult.setData("提交成功");
        return jsonResult;
    }

    @RequestMapping(value = "addInfo",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult add(@Validated @RequestParam(value = "name",required = true) String name, @RequestParam("attachs") MultipartFile[] attachs, HttpServletResponse response, HttpServletRequest req)  throws IOException{
        JsonResult jsonResult = new JsonResult();
//        Scenic scenic = new Scenic();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Cache-Control","no-cache");
        Scenic scenic = new Scenic();

//        if (br.hasErrors()) {
//            //如果有错误直接跳转到上传页面
//            jsonResult.setCode(1);
//            jsonResult.setMsg("failed");
//            jsonResult.setData("");
//            return jsonResult;
//        }

        //获取当前工程文件路径
       String realpath = this.getClass().getResource("/static/upload/").getPath();
        //       logger.info("product-------add-----------realPath:" + JSON.toJSON(realpath));
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                continue;
            }
            //如果上传图片类型不是图片则返回上传页面
            BufferedImage bi = ImageIO.read(attach.getInputStream());
            if (bi==null){
//                map.put("code","405");
//                map.put("msg","input null");
                jsonResult.setCode(2);
                jsonResult.setMsg("not image");
                jsonResult.setData("上传的图片为空");
                return jsonResult;
            }
//            String pic_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//            PicUploadUtil.formatDate(new Date());
            String picName = PicUploadUtil.picRename(Objects.requireNonNull(attach.getContentType()));
//            String picName = product.getName();
            String path = realpath + "/" + picName;
            File f = new File(path);
//            logger.info("product添加realpath:" + JSON.toJSON(realpath));
//            logger.info("product添加getOriginalFilename:" + JSON.toJSON(attach.getOriginalFilename()));
//            logger.info("product添加path:" + JSON.toJSON(path));
//            product.setImg(picName);
            scenic.setPicturePath("http://localhost:8080/pictrue/"+picName);
//            productService.addImg(product);
            scenicMapper.updateByName(name,"http://localhost:8080/pictrue/"+picName);
            System.out.println(name);
            try {

                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        logger.info("product添加Post:" + JSON.toJSON(product));

//        System.out.println("product里面的商品信息:" + JSON.toJSON(product));
//        productService.add(product.getcId(), product)

        jsonResult.setCode(0);
        jsonResult.setMsg("success");
        jsonResult.setData(realpath);
        return jsonResult;
    }



    @RequestMapping(value="/uploadAudio",produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult uploadFile(@RequestParam("attachs") MultipartFile[] files,@Validated @RequestParam(value = "name",required = true) String name) {
        JsonResult jsonResult = new JsonResult();
        System.out.print("上传文件==="+"\n");
        for (MultipartFile file : files){
            //判断文件是否为空
            if (file.isEmpty()) {
                jsonResult.setCode(1);
                jsonResult.setMsg("file null");
                jsonResult.setData("文件为空");
                return jsonResult;
            }
            // 获取文件名
            String fileName = Objects.requireNonNull(file.getOriginalFilename()).replace(" ","");
            fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
            System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: "+fileName+"\n");
            //获取当前工程文件路径
            String realpath = this.getClass().getResource("/static/upload/").getPath();
            //加个时间戳，尽量避免文件名称重复
            String path = realpath + fileName;
            //String path = "E:/fileUpload/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
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
                url="http://localhost:8080/audio/"+fileName;
                int final_res = scenicMapper.updateAudioPathByName(name,url);
                jsonResult.setCode(0);
                jsonResult.setMsg("success");
                jsonResult.setData(final_res);


            } catch (IOException e) {
                jsonResult.setCode(3);
                jsonResult.setMsg("failed");
                jsonResult.setData("上传失败");
                return jsonResult;
            }

        }




//        System.out.print("上传的文件名为: "+fileName+"\n");





        return jsonResult;
    }

}