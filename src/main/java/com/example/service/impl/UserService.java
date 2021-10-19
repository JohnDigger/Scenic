package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.service.UserServiceimpl;
import com.example.model.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Service
public class UserService implements UserServiceimpl {

    @Autowired(required = false)
    UserMapper userMapper;

    @Override
    public JsonResult userLogin(String username, String password, HttpServletRequest request) {
        User userMsg = userMapper.findByName(username);
        JsonResult jsonResult = new JsonResult();
        HttpSession session = request.getSession();
        if (userMsg == null) {
            jsonResult.setCode(2);
            jsonResult.setMsg("user do not exist");
            jsonResult.setData("null");
            return jsonResult;
        }
        if (userMsg.getPassword().equals(password)) {
            jsonResult.setCode(0);
            jsonResult.setMsg("login success");
            jsonResult.setData(userMsg);
            User user = userMapper.findByName(username);
            session.setAttribute("USER", user);
        } else {
            jsonResult.setCode(1);
            jsonResult.setMsg("login failed");
            jsonResult.setData("null");
        }
        return jsonResult;

    }

    @Override
    public User queByName(String name) {
        return userMapper.findByName(name);
    }


//    @Override
//    public List<User> findAllUser() {
//        return userMapper.findAllUser();
//    }
//
//    @Override
//    public User findByName(String name) {
//        return userMapper.findByName(name);
//    }
//
//    @Override
//    public String register(String username, String password) {
//
//        User user = userMapper.findByName(username);
//
//        if(user == null){
//            User userNew = new User();
//            userNew.setName(username);
//            userNew.setPassword(password);
//            userMapper.insert(userNew);
//        }else{
//            return "用户名已注册";
//        }
//        return "注册成功";
//    }
//
//    @Override
//    public String updateUser(int id,User user){
//
//        User userNew = userMapper.findById(id);
//        userNew.setName(user.getName());
//        userNew.setPassword(user.getPassword());
//        userMapper.updateById(userNew);
//        return "更新成功";
//    }
//
//
//    @Override
//    public String deleteById(int id) {
//        userMapper.deleteById(id);
//        return "删除成功";
//    }


}


