package com.tom.dao.system;

import com.tom.domain.system.Module;
import com.tom.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleDao {
    //查询全部
    List<Module> findAll();

    //根据id查询
    Module findById(String id);

    //添加用户
    void save(Module module);

    //更新用户
    void update(Module module);

    //根据id删除
    void delete(String id);

    List<Module> findModulesByRoleId(String roleId);

    void deleteModulesByRoleId(String roleId);

    void saveRoleModule(@Param("roleId") String roleId, @Param("moduleId") String moduleId);

    List<Module> findByBelong(Integer belong);

    List<Module> findByUserId(String userId);
}
