package com.tom.service.cargo.impl;

import com.tom.dao.cargo.FactoryDao;
import com.tom.domain.cargo.Factory;
import com.tom.domain.cargo.FactoryExample;
import com.tom.service.cargo.FactoryService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    private FactoryDao factoryDao;

    @Override
    public void save(Factory factory) {

    }

    @Override
    public void update(Factory factory) {

    }

    @Override
    public void delete(String id) {
       
    }

    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Factory> findAll(FactoryExample example) {
        return factoryDao.selectByExample(example);
    }

    @Override
    public PageInfo findByPage(int pageNum, int pageSize, FactoryExample example) {
        PageHelper.startPage(pageNum, pageSize);
        List<Factory> list = factoryDao.selectByExample(example);
        return new PageInfo(list);
    }
}
