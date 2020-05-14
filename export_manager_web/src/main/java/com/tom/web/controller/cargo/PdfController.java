package com.tom.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tom.domain.cargo.Export;
import com.tom.domain.cargo.ExportProduct;
import com.tom.domain.cargo.ExportProductExample;
import com.tom.service.cargo.ExportProductService;
import com.tom.service.cargo.ExportService;
import com.tom.utils.BeanMapUtils;
import com.tom.utils.DownloadUtils;
import com.tom.web.controller.BaseController;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/cargo/export", name = "pdf报表")
public class PdfController extends BaseController {
    @Reference
    private ExportService exportService;

    @Reference
    ExportProductService exportProductService;

    @RequestMapping(path = "/exportPdf", name = "pdf下载预览")
    public void exportPdf(String id) throws Exception {
        Export export = exportService.findById(id);

        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> list = exportProductService.findAll(example);

        Map<String, Object> map = BeanMapUtils.beanToMap(export);

        JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);

        String realPath = session.getServletContext().getRealPath("/jasper/export.jasper");

        JasperPrint jasperPrint = JasperFillManager.fillReport(realPath, map, jrDataSource);

//        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
        DownloadUtils.download(byteArrayOutputStream, response, "报运单.pdf");
    }
}
