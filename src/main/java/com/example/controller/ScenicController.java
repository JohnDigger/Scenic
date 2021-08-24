package com.example.controller;

import com.example.mapper.ScenicMapper;
import com.example.model.*;
import com.example.service.IScenicService;
import com.example.model.JsonResult;
import com.example.util.FileNameUtil;
import com.example.util.PicUploadUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @Autowired(required = false)
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

    @RequestMapping("/getScenicByType")
    public List<Scenic> getScenicByType(String scenicType) {
        return scenicService.getScenicByType(scenicType);
    }

    @RequestMapping("/search")
    public List<Scenic> searchScenic(String Sname){
        return scenicService.searchScenic(Sname);
    }

    @RequestMapping("/getPage")
    public List<Scenic> getPage(int start,int num) {
        return scenicService.getPage(start,num);
    }








    //后台管理
    @RequestMapping(value = "/queryMsg",method = RequestMethod.POST)
    public JsonResult queryMsg(@RequestParam(name = "page",required = false,defaultValue = "0") int page,@RequestParam(name = "limit",required = false,defaultValue = "10") int limit){
        return scenicService.queryMsg(page, limit);
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
    public JsonResult textInsert(@RequestParam String name,@RequestParam String list,@RequestParam String text,@RequestParam String type){
        System.out.println(name);
        JsonResult jsonResult = new JsonResult();
        Scenic scenic = new Scenic();
        scenic.setName(name);
        scenic.setList(list);
        scenic.setText(text);
        scenic.setType(type);
        try {

            scenicMapper.InsertScenic(scenic);
        }catch (Exception e){
            e.printStackTrace();
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
//       String realpath = this.getClass().getResource("/static/upload/").getPath();
        String realpath = "C:/scenic/picture/";
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
            scenic.setPicturePath("http://localhost:80/picture/"+picName);
//            productService.addImg(product);
            scenicMapper.updateByName(name,"http://localhost:80/picture/"+picName);
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



    @RequestMapping(value="/uploadVideo",produces="application/json;charset=UTF-8")
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

            }

            // 获取文件名
            String fileName = Objects.requireNonNull(file.getOriginalFilename()).replace(" ","");
            fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
            fileName = FileNameUtil.getFileName(fileName);
            System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: "+fileName+"\n");
            //获取当前工程文件路径
            String realpath = "C:/scenic/video/";
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
                url="http://localhost:80/video/"+fileName;
                return scenicService.updateVideo(name,url);

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