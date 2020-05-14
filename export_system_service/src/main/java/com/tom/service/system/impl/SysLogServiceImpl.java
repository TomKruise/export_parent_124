package com.tom.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tom.dao.system.SysLogDao;
import com.tom.domain.system.SysLog;
import com.tom.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public List<SysLog> findAll(String companyId) {
        return sysLogDao.findAll(companyId);
    }

    @Override
    public void save(SysLog log) {
        log.setId(UUID.randomUUID().toString());
        sysLogDao.save(log);
    }

    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, String companyId) {
        PageHelper.startPage(pageNum, pageSize);

        List<SysLog> list = sysLogDao.findAll(companyId);

        return new PageInfo(list);
    }
}
