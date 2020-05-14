package com.tom.dao.system;

import com.tom.domain.system.SysLog;

import java.util.List;

public interface SysLogDao {
    //查询全部
    List<SysLog> findAll(String companyId);

    //添加
    void save(SysLog log);
}
