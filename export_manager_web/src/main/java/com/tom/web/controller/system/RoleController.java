package com.tom.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.tom.domain.system.Module;
import com.tom.domain.system.Role;
import com.tom.service.system.ModuleService;
import com.tom.service.system.RoleService;
import com.tom.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        PageInfo page = roleService.findByPage(pageNum, pageSize, getCompanyId());
        request.setAttribute("page", page);
        return "system/role/role-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "system/role/role-add";
    }

    @RequestMapping("/edit")
    public String edit(Role role) {
        role.setCompanyId(getCompanyId());
        role.setCompanyName(getCompanyName());

        if (StringUtils.isEmpty(role.getId())) {
            roleService.save(role);
        } else {
            roleService.update(role);
        }

        return "redirect:/system/role/list.do";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        Role role = roleService.findById(id);
        request.setAttribute("role", role);
        return "system/role/role-update";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }

    @RequestMapping("/roleModule")
    public String roleModule(String roleId) {
        Role role = roleService.findById(roleId);

        request.setAttribute("role", role);
        return "system/role/role-module";
    }

    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/getZtreeNodes")
    @ResponseBody
    public List<Map> getZtreeNodes(String roleId) {
        List<Map> list = new ArrayList<>();

        List<Module> moduleList = moduleService.findAll();

        List<Module> roleModuleList = moduleService.findModulesByRoleId(roleId);

        for (Module module : moduleList) {
            Map map = new HashMap();
            map.put("id", module.getId());
            map.put("pId", module.getParentId());
            map.put("name", module.getName());

            if (roleModuleList.contains(module)) {
                map.put("checked", true);
            }

            list.add(map);
        }

        return list;
    }

    @RequestMapping("/updateRoleModule")
    public String updateRoleModule(String roleId, String moduleIds) {
        moduleService.updateRoleModule(roleId, moduleIds);

        return "redirect:/system/role/list.do";
    }
}
