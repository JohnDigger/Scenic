package com.example.controller;


import com.example.model.User;
import com.example.service.UserServiceimpl;
import com.example.model.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@Controller
@RequestMapping(produces = "application/json; charset=utf-8")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {

    @Autowired
    UserServiceimpl userServiceimpl;

    @RequestMapping(value = "userlogin")
    @ResponseBody
    public JsonResult userLogin(@RequestParam("username") String username,@RequestParam("password") String password,HttpServletRequest request){

        return userServiceimpl.userLogin(username,password,request);
    }
    @RequestMapping("login")
    public String login(){

        return "login";
    }
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        User user=(User)request.getSession().getAttribute("USER");
        if(user==null){
            return "login";
        }
//            response.sendRedirect(request.getContextPath()+"/login");
        return "index";


    }
    @RequestMapping("addInfo")
    public String addInfo(HttpServletRequest request){
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        User user=(User)request.getSession().getAttribute("USER");
        if(user==null){
            return "login";
        }

        return "addObj";
    }
    @RequestMapping("imgUpload")
    public String imgUpload(HttpServletRequest request){
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        User user=(User)request.getSession().getAttribute("USER");
        if(user==null){
            return "login";
        }

        return "uploadFile";
    }

    @RequestMapping("audUpload")
    public String audUpload(HttpServletRequest request){
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        User user=(User)request.getSession().getAttribute("USER");
        if(user==null){
            return "login";
        }

        return "uploadAudio";
    }


    /**
     * 登出操作
     * @param request
     * @return
     */
    @GetMapping(value = "/loginout")
    @ResponseBody
    public String loginout(HttpServletRequest request) {


        HttpSession session = request.getSession();

        // 将用户信息从session中删除
        session.removeAttribute("USER");

        Object USER = session.getAttribute("USER");
        if (USER == null) {
            return "登出成功";
        } else {
            return "登出失败";
        }

    }
    @RequestMapping("purchas")
    public String purchas(HttpServletRequest request){
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        User user=(User)request.getSession().getAttribute("USER");
        if(user==null){
            return "login";
        }

        return "purchase_infomation";
    }

    @RequestMapping("pay")
    public String paid(HttpServletRequest request){
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        User user=(User)request.getSession().getAttribute("USER");
        if(user==null){
            return "login";
        }

        return "paid_information";
    }
    @RequestMapping("addScenic")
    public String AddScenic(HttpServletRequest request){
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        User user=(User)request.getSession().getAttribute("USER");
        if(user==null){
            return "login";
        }

        return "addScenic";
    }
    @RequestMapping("update")
    public String update(HttpServletRequest request){
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        User user=(User)request.getSession().getAttribute("USER");
        if(user==null){
            return "login";
        }

        return "layer_update";
    }
    @RequestMapping("audioList")
    public String audioList(HttpServletRequest request){
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        User user=(User)request.getSession().getAttribute("USER");
        if(user==null){
            return "login";
        }

        return "AudioList";
    }
    @RequestMapping("audioUpdate")
    public String audioUpdate(HttpServletRequest request){
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        User user=(User)request.getSession().getAttribute("USER");
        if(user==null){
            return "login";
        }

        return "audio_update";
    }
}






