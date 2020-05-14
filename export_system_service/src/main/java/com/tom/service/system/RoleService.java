package com.tom.service.system;

import com.github.pagehelper.PageInfo;
import com.tom.domain.system.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    //查询全部用户
    List<Role> findAll(String companyId);

    //根据id查询
    Role findById(String id);

    //添加
    void save(Role role);

    //更新
    void update(Role role);

    //根据id删除
    void delete(String id);

    PageInfo findByPage(Integer pageNum, Integer pageSize, String companyId);

    List<Role> findByUserId(String id);

    void changeRole(String userId, String[] roleIds);
}
