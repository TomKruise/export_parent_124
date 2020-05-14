package com.tom.service.company;

import com.github.pagehelper.PageInfo;
import com.tom.domain.company.Company;
import com.tom.domain.vo.PageBean;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();

    Company findById(String id);

    void save(Company company);

    void update(Company company);

    void delete(String id);

    //传统分页
    PageBean findByPage(Integer pageNum, Integer pageSize);

    //分页插件
    PageInfo findByPageHelper(Integer pageNum, Integer pageSize);
}
