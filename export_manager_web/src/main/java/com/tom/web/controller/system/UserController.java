package com.tom.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.tom.domain.system.Dept;
import com.tom.domain.system.Role;
import com.tom.domain.system.User;
import com.tom.service.system.DeptService;
import com.tom.service.system.RoleService;
import com.tom.service.system.UserService;
import com.tom.utils.MailUtils;
import com.tom.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/system/user", name = "用户管理")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @RequestMapping(path = "/list.do", name = "查询用户列表")
    @RequiresPermissions("用户管理") //相当于配置/system/user/list.do = perms["用户管理"]
    public String list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        //分页插件
        PageInfo pageInfo = userService.findByPage(pageNum, pageSize, getCompanyId());

        request.setAttribute("page", pageInfo);

        return "system/user/user-list";
    }

    @RequestMapping(path = "/toAdd", name = "新增页面")
    public String toAdd() {
        List<Dept> list = deptService.findAll(getCompanyId());

        request.setAttribute("deptList", list);

        return "system/user/user-add";
    }

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping(path = "/edit", name = "save or update")
    public String edit(User user) {
        user.setCompanyId(getCompanyId());
        user.setCompanyName(getCompanyName());

        String to = user.getEmail();
        String title = "saas_Export企业："+user.getCompanyName();
        String content = user.getUserName() + "用户,恭喜您注册成功。<br/>登录邮箱："+user.getEmail()+"，登录密码："+user.getPassword();


        if (StringUtils.isEmpty(user.getId())) {
            //用户输入的密码在6-12位之间
            if (user.getPassword().length() < 32) {
                //实现对密码加密
                String passwordMd5 = new Md5Hash(user.getPassword(), user.getEmail(), 2).toString();
                user.setPassword(passwordMd5);
            }
            userService.save(user);
//            MailUtils.sendMail(to, title, content);

            Map<String, String> map = new HashMap<>();

            map.put("to", to);
            map.put("title", title);
            map.put("content", content);

            amqpTemplate.convertAndSend("mail.send", map);
        } else {
            userService.update(user);
        }
        return "redirect:/system/user/list.do";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //根据id查询用户
        User user = userService.findById(id);
        request.setAttribute("user", user);

        //查询部门列表
        List<Dept> list = deptService.findAll(getCompanyId());

        request.setAttribute("deptList", list);

        return "/system/user/user-update";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        userService.delete(id);
        return "redirect:/system/user/list.do";
    }

    @Autowired
    private RoleService roleService;

    @RequestMapping("/roleList")
    public String roleList(String id) {
        User user = userService.findById(id);
        request.setAttribute("user", user);

        List<Role> roleList = roleService.findAll(getCompanyId());
        request.setAttribute("roleList", roleList);

        //回显已分配的角色信息
        List<Role> userRoleList = roleService.findByUserId(id);
        StringBuilder userRoleStr = new StringBuilder();
        for (Role role : userRoleList) {
            userRoleStr.append(role.getId()).append(",");
        }
        request.setAttribute("userRoleStr", userRoleStr.toString());
        return "system/user/user-role";
    }

    @RequestMapping("/changeRole")
    public String changeRole(String userId, String[] roleIds) {
        roleService.changeRole(userId, roleIds);

        return "redirect:/system/user/list.do";
    }
}
