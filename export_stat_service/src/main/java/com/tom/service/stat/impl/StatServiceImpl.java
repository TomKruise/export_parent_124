package com.tom.service.stat.impl;

import com.tom.dao.stat.StatDao;
import com.tom.service.stat.StatService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {


    @Autowired
    private StatDao statDao;

    @Override
    public List<Map> findFactoryCharts(String companyId) {
        return statDao.findFactoryCharts(companyId);
    }

    @Override
    public List<Map> findSellCharts(String companyId) {
        return statDao.findSellCharts(companyId);
    }

    @Override
    public List<Map> findOnlineCharts(String companyId) {
        return statDao.findOnlineCharts(companyId);
    }
}
