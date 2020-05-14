package com.tom.service.company.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tom.dao.company.CompanyDao;
import com.tom.domain.company.Company;
import com.tom.domain.vo.PageBean;
import com.tom.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;
    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    @Override
    public void save(Company company) {
        company.setId(UUID.randomUUID().toString());
        companyDao.save(company);
    }

    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }

    @Override
    public PageBean findByPage(Integer pageNum, Integer pageSize) {
        Long total = companyDao.findCount();

        Integer index = (pageNum - 1) * pageSize;

        List<Company> list = companyDao.findList(index, pageSize);
        return new PageBean(pageNum, pageSize, total, list);
    }

    @Override
    public PageInfo findByPageHelper(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Company> list = companyDao.findAll();

        return new PageInfo(list);
    }
}
