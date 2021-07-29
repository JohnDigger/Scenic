package com.example.service;

import com.example.model.User;
import com.example.model.JsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserServiceimpl {

    JsonResult userLogin(String username, String password, HttpServletRequest request);

    User queByName(String name);
}