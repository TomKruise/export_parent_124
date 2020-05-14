package com.tom.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tom.domain.cargo.*;
import com.tom.service.cargo.ContractService;
import com.tom.service.cargo.ExportProductService;
import com.tom.service.cargo.ExportService;
import com.tom.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/cargo/export", name = "报运单管理")
public class ExportController extends BaseController {
    @Reference
    private ContractService contractService;

    @RequestMapping(path = "/contractList", name = "合同状态为1的列表")
    public String contractList(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5")Integer pageSize) {
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andStateEqualTo(1).andCompanyIdEqualTo(getCompanyId());
        PageInfo page = contractService.findByPage(pageNum, pageSize, contractExample);

        request.setAttribute("page", page);
        return "cargo/export/export-contractList";
    }

    @Reference
    private ExportService exportService;

    @RequestMapping(path = "/list", name = "合同状态为1的列表")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5")Integer pageSize) {
        ExportExample exportExample = new ExportExample();
        exportExample.createCriteria().andCompanyIdEqualTo(getCompanyId());
        PageInfo page = exportService.findByPage(pageNum, pageSize, exportExample);
        request.setAttribute("page", page);
        return "cargo/export/export-list";
    }

    @RequestMapping(path = "/toExport", name = "报运单新增页面")
    public String toExport(String id) {
        request.setAttribute("id", id);
        return "cargo/export/export-toExport";
    }

    @RequestMapping(path = "/edit", name = "save or update")
    public String edit(Export export) {
        export.setCompanyId(getCompanyId());
        export.setCompanyName(getCompanyName());
        if (StringUtils.isEmpty(export.getId())) {
            exportService.save(export);
        } else {
            exportService.update(export);
        }

        return "redirect:/cargo/export/list.do";
    }

    @Reference
    private ExportProductService exportProductService;

    @RequestMapping(path = "/toUpdate", name = "报运单修改页面")
    public String toUpdate(String id) {
        Export export = exportService.findById(id);
        request.setAttribute("export", export);

        ExportProductExample epExample = new ExportProductExample();
        epExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> eps = exportProductService.findAll(epExample);
        request.setAttribute("eps", eps);

        return "cargo/export/export-update";
    }

    @RequestMapping(path = "/exportE", name = "电子报运")
    public String exportE(String id) {
        exportService.exportE(id);
        return "redirect:/cargo/export/list.do";
    }
}
