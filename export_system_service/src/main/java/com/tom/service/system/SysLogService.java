package com.tom.service.system;

import com.github.pagehelper.PageInfo;
import com.tom.domain.system.SysLog;

import java.util.List;

public interface SysLogService {
    //查询全部
    List<SysLog> findAll(String companyId);

    //添加
    void save(SysLog log);

    public PageInfo findByPage(Integer pageNum, Integer pageSize, String companyId);
}
