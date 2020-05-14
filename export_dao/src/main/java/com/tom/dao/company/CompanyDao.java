package com.tom.dao.company;

import com.tom.domain.company.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyDao {
    List<Company> findAll();

    Company findById(String id);

    void save(Company company);

    void update(Company company);

    void delete(String id);

    Long findCount();

    List<Company> findList(@Param("index") Integer index, @Param("pageSize") Integer pageSize);
}
