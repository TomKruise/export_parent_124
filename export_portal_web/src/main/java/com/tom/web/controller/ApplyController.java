package com.tom.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tom.domain.company.Company;
import com.tom.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplyController {
    @Reference
    private CompanyService companyService;

    @RequestMapping(path = "/apply", name = "企业申请")
    @ResponseBody
    public String apply(Company company) {
        company.setState(0);//未审核
        try {
            companyService.save(company);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

        return "1";
    }
}
