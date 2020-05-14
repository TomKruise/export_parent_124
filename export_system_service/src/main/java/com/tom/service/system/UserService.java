package com.tom.service.system;

import com.github.pagehelper.PageInfo;
import com.tom.domain.system.Role;
import com.tom.domain.system.User;

import java.util.List;

public interface UserService {
    //根据企业id查询全部
    List<User> findAll(String companyId);

    //根据id查询
    User findById(String userId);

    //保存
    void save(User user);

    //更新
    void update(User user);

    //根据id删除
    void delete(String userId);

    PageInfo findByPage(Integer pageNum, Integer pageSize, String companyId);

    User findByEmail(String email);
}
