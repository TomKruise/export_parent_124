package com.tom.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tom.domain.cargo.ExtCproduct;
import com.tom.domain.cargo.ExtCproductExample;
import com.tom.domain.cargo.Factory;
import com.tom.domain.cargo.FactoryExample;
import com.tom.service.cargo.ExtCproductService;
import com.tom.service.cargo.FactoryService;
import com.tom.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/cargo/extCproduct", name = "附件管理")
public class ExtCproductController extends BaseController {
    @Reference
    private ExtCproductService extCproductService;

    @Reference
    private FactoryService factoryService;

    @RequestMapping(path = "/list", name = "附件查询")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, String contractId, String contractProductId) {
        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andContractProductIdEqualTo(contractProductId);
        PageInfo extCproductServiceByPage = extCproductService.findByPage(pageNum, pageSize, extCproductExample);
        request.setAttribute("page", extCproductServiceByPage);

        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);

        request.setAttribute("factoryList", factoryList);

        request.setAttribute("contractId", contractId);

        request.setAttribute("contractProductId", contractProductId);
        return "cargo/extc/extc-list";
    }

    @RequestMapping(path = "/edit", name = "save or update")
    public String edit(ExtCproduct extc) {
        extc.setCompanyId(getCompanyId());
        extc.setCompanyName(getCompanyName());

        extCproductService.save(extc);

        return "redirect:/cargo/extCproduct/list.do?contractId=" + extc.getContractId() + "&contractProductId=" + extc.getContractProductId();
    }

    @RequestMapping(path = "/toUpdate", name = "update")
    public String toUpdate(String id) {
        ExtCproduct extCproduct = extCproductService.findById(id);
        request.setAttribute("extCproduct", extCproduct);

        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);

        request.setAttribute("factoryList", factoryList);

        return "cargo/extc/extc-update";
    }

    @RequestMapping(path = "/delete", name = "attachment")
    public String delete(String id, String contractId, String contractProductId) {
        extCproductService.delete(id);

        return "redirect:/cargo/extCproduct/list.do?contractId=" + contractId + "&contractProductId=" + contractProductId;

    }
}
