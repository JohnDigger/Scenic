package com.example.controller;


import com.example.model.JsonResult;
import com.example.model.User;
import com.example.service.UserServiceimpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(produces = {"application/json; charset=utf-8"})
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class UserController {
    @Autowired
    UserServiceimpl userServiceimpl;

    @RequestMapping({"userlogin"})
    @ResponseBody
    public JsonResult userLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        return this.userServiceimpl.userLogin(username, password, request);
    }

    @RequestMapping({"login"})
    public String login() {
        return "login";
    }

    @RequestMapping({"index"})
    public String index(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "index";
    }

    @RequestMapping({"addInfo"})
    public String addInfo(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "addObj";
    }

    @RequestMapping({"imgUpload"})
    public String imgUpload(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "uploadFile";
    }

    @RequestMapping({"audUpload"})
    public String audUpload(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "uploadAudio";
    }

    @GetMapping({"/loginout"})
    public String loginout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("USER");
        Object USER = session.getAttribute("USER");
        if (USER == null)
            return "login";
        return "/index";
    }

    @RequestMapping({"purchas"})
    public String purchas(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "purchase_infomation";
    }

    @RequestMapping({"pay"})
    public String paid(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "paid_information";
    }

    @RequestMapping({"addScenic"})
    public String AddScenic(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "addScenic";
    }

    @RequestMapping({"update"})
    public String update(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "layer_update";
    }

    @RequestMapping({"audioList"})
    public String audioList(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "AudioList";
    }

    @RequestMapping({"audioUpdate"})
    public String audioUpdate(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "audio_update";
    }
    @RequestMapping({"header"})
    public String header(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "header/header";
    }
    @RequestMapping({"addScnicType"})
    public String addScnicType(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "addType";
    }

    @RequestMapping({"updateScenicFile"})
    public String updatScenicFile(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("USER");
        if (user == null)
            return "login";
        return "uploadScenicFile";
    }
}





