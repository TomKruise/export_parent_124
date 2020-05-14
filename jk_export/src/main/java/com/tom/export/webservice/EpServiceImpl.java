package com.tom.export.webservice;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;

import com.tom.export.domain.Export;
import com.tom.export.domain.ExportProduct;
import com.tom.export.service.ExportProductService;
import com.tom.export.service.ExportService;
import com.tom.export.vo.ExportProductResult;
import com.tom.export.vo.ExportResult;
import com.tom.export.vo.ExportVo;

public class EpServiceImpl implements EpService {

    private ExportService exportService;
    private ExportProductService exportProductService;

    public void setExportService(ExportService exportService) {
        this.exportService = exportService;
    }

    public void setExportProductService(ExportProductService exportProductService) {
        this.exportProductService = exportProductService;
    }

    //实现报运单的数据保存  exportVo---export   exportProductVo-----exportProduct
    public void exportE(ExportVo exportVo) throws Exception {
        Export export = new Export();
        org.springframework.beans.BeanUtils.copyProperties(exportVo, export);
        System.out.println(JSON.toJSONString(export));
        Set<ExportProduct> epSet = export.getProducts();

        exportService.saveOrUpdate(export);

        for (ExportProduct ep : epSet) {
            ExportProduct epObj = new ExportProduct();
            BeanUtils.copyProperties(ep, epObj);
            exportProductService.saveOrUpdate(epObj);
        }
    }

    //实现报运单信息的查询，并响应给Webservice客户端
    public ExportResult getResult(String id) throws Exception {
        Export result = exportService.get(Export.class, id);
        ExportResult exportR = new ExportResult();
        exportR.setExportId(result.getExportId());
        exportR.setState(2);
        exportR.setRemark("报运成功");

        Set<ExportProductResult> epResult = new HashSet<ExportProductResult>();
        double i = 1;
        List<ExportProduct> epList = exportProductService.find("from ExportProduct where exportId=?",
                ExportProduct.class, new String[]{id});

        for (ExportProduct ep : epList) {
            ExportProductResult eprObj = new ExportProductResult();
            eprObj.setExportProductId(ep.getExportProductId());
            eprObj.setTax(10 + (i++) * 0.4);

            epResult.add(eprObj);
        }
        exportR.setProducts(epResult);
        return exportR;
    }
}