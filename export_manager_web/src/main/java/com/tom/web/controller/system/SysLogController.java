package com.tom.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.tom.service.system.SysLogService;
import com.tom.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "5") Integer pageSize) {
        PageInfo page = sysLogService.findByPage(pageNum, pageSize, getCompanyId());
        request.setAttribute("page", page);
        return "system/log/log-list";
    }
}
