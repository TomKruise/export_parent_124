package com.tom.dao.system;


import com.tom.domain.system.Dept;

import java.util.List;

public interface DeptDao {
    List<Dept> findAll(String companyId);

    Dept findById(String id);

    void save(Dept dept);

    void update(Dept dept);

    void delete(String id);
}
