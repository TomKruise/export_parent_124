package com.tom.dao.system;

import com.tom.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {
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

    List<Role> findByUserId(String userId);

    void deleteByUserId(String userId);

    void saveUserRole(@Param("userId") String userId, @Param("roleId") String roleId);
}
