package com.tom.service.cargo;

import com.tom.domain.cargo.Export;
import com.tom.domain.cargo.ExportExample;
import com.github.pagehelper.PageInfo;
import com.tom.domain.cargo.ExportResult;

public interface ExportService {

    Export findById(String id);

    void save(Export export);

    void update(Export export);

    void delete(String id);

    PageInfo findByPage(int pageNum, int pageSize, ExportExample example);

    void exportE(String id);

    void updateE(ExportResult exportResult);
}
