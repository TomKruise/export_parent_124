package com.tom.web.controller.system;


import com.github.pagehelper.PageInfo;
import com.tom.domain.system.Module;
import com.tom.service.system.ModuleService;
import com.tom.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {
    @Autowired
    private ModuleService moduleService;

    @RequestMapping(path = "/list.do", name = "查询模块列表")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        //分页插件
        PageInfo pageInfo = moduleService.findByPage(pageNum, pageSize);

        request.setAttribute("page", pageInfo);

        return "system/module/module-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        List<Module> l = moduleService.findAll();
        request.setAttribute("menus", l);
        return "system/module/module-add";
    }

    @RequestMapping("/edit")
    public String edit(Module module) {
        if (StringUtils.isEmpty(module.getId())) {
            moduleService.save(module);
        } else {
            moduleService.update(module);
        }

        return "redirect:/system/module/list.do";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        Module module = moduleService.findById(id);
        request.setAttribute("module", module);

        List<Module> l = moduleService.findAll();
        request.setAttribute("menus", l);

        return "system/module/module-update";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }
}
