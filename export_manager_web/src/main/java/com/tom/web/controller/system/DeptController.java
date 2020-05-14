package com.tom.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.tom.domain.system.Dept;
import com.tom.service.system.DeptService;
import com.tom.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/system/dept", name = "部门管理")
public class DeptController extends BaseController {
    @Autowired
    private DeptService deptService;

    @RequestMapping(path = "/list.do", name = "查询部门列表")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        //分页插件
        PageInfo pageInfo = deptService.findByPage(pageNum, pageSize, getCompanyId());

        request.setAttribute("page", pageInfo);

        return "system/dept/dept-list";
    }

    @RequestMapping(path = "/toAdd", name = "新增页面")
    public String toAdd() {
        List<Dept> deptList = deptService.findAll(getCompanyId());
        request.setAttribute("deptList", deptList);
        return "system/dept/dept-add";
    }

    @RequestMapping(path = "/edit", name = "save or update")
    public String edit(Dept dept) {
        dept.setCompanyId(getCompanyId());
        dept.setCompanyName(getCompanyName());

        if (StringUtils.isEmpty(dept.getId())) {
            deptService.save(dept);
        } else {
            deptService.update(dept);
        }
        return "redirect:/system/dept/list.do";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //根据id查询部门
        Dept dept = deptService.findById(id);
        request.setAttribute("dept", dept);

        //查询部门列表
        List<Dept> deptList = deptService.findAll(getCompanyId());
        request.setAttribute("deptList", deptList);

        return "/system/dept/dept-update";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        deptService.delete(id);
        return "redirect:/system/dept/list.do";
    }
}
