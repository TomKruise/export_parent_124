package com.tom.service.system;

import com.github.pagehelper.PageInfo;
import com.tom.domain.system.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> findAll(String companyId);

    Dept findById(String id);

    void save(Dept dept);

    void update(Dept dept);

    void delete(String id);

    PageInfo findByPage(Integer pageNum, Integer pageSize, String companyId);
}
