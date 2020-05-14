package com.tom.web.controller;

import com.tom.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;

    public User getUser() {
        return (User) session.getAttribute("loginUser");
    }

    public String getCompanyId() {
        return getUser().getCompanyId();
    }

    public String getCompanyName() {
        return getUser().getCompanyName();
    }
}
