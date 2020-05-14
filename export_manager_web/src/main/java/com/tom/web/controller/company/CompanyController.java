package com.tom.web.controller.company;

import com.github.pagehelper.PageInfo;
import com.tom.domain.company.Company;
import com.tom.service.company.CompanyService;
import com.tom.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/company", name = "企业管理")
public class CompanyController extends BaseController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping(path = "/list.do", name = "查询企业列表")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
//        List<Company> list = companyService.findAll();
//        request.setAttribute("list", list);

        //传统分页
//        PageBean bean = companyService.findByPage(pageNum, pageSize);
//        request.setAttribute("page", bean);

        //分页插件
        PageInfo pageInfo = companyService.findByPageHelper(pageNum, pageSize);
        request.setAttribute("page", pageInfo);

        return "company/company-list";
    }

    @RequestMapping(path = "/toAdd", name = "新增页面")
    public String toAdd() {
        return "company/company-add";
    }

    @RequestMapping(path = "/edit", name = "save or update")
    public String edit(Company company) {
        if (StringUtils.isEmpty(company.getId())) {
            companyService.save(company);
        } else {
            companyService.update(company);
        }


        return "redirect:/company/list.do";
    }

    @RequestMapping(path = "/toUpdate", name = "修改页面")
    public String toUpdate(String id) {
        Company company = companyService.findById(id);
        request.setAttribute("company", company);
        return "company/company-update";
    }

    @RequestMapping(path = "/delete", name = "删除页面")
    public String delete(String id) {
        companyService.delete(id);
        return "redirect:/company/list.do";
    }
}
