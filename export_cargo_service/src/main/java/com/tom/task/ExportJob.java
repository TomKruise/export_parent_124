package com.tom.task;

import com.github.pagehelper.PageInfo;
import com.tom.domain.cargo.Export;
import com.tom.domain.cargo.ExportExample;
import com.tom.domain.cargo.ExportResult;
import com.tom.service.cargo.ExportService;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExportJob {
    @Autowired
    private ExportService exportService;

    public void run() {
        ExportExample exportExample = new ExportExample();
        exportExample.createCriteria().andStateEqualTo(1L);
        PageInfo page = exportService.findByPage(1, 100, exportExample);

        List list = page.getList();
        if (null != list && list.size() > 0) {
            for (Object o : list) {
                Export export = (Export)o;
                WebClient wc = WebClient.create("http://localhost:8088/ws/export/user" + export.getId());

                ExportResult exportResult = wc.get(ExportResult.class);

                if ((exportResult != null && export.getState() == 2)) {
                    exportService.updateE(exportResult);
                }
            }
        }
    }
}
