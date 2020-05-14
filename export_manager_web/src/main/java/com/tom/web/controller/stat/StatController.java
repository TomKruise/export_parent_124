package com.tom.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tom.service.stat.StatService;
import com.tom.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/stat", name = "统计分析")
public class StatController extends BaseController {
    @Reference
    private StatService statService;

    @RequestMapping(path = "/toCharts", name = "基础页面跳转")
    public String toCharts(String chartsType) {
        return "stat/stat-"+chartsType;
    }

    @RequestMapping(path = "/factoryCharts", name = "厂家销售统计")
    @ResponseBody
    public List<Map> factoryCharts() {
        return statService.findFactoryCharts(getCompanyId());
    }

    @RequestMapping(path = "/sellCharts", name = "产品销量排行榜")
    @ResponseBody
    public List<Map> sellCharts() {
        return statService.findSellCharts(getCompanyId());
    }

    @RequestMapping(path = "/onlineCharts", name = "系统访问统计")
    @ResponseBody
    public List<Map> onlineCharts() {
        return statService.findOnlineCharts(getCompanyId());
    }
}
