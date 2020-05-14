package com.tom.service.system;

import com.github.pagehelper.PageInfo;
import com.tom.domain.system.Module;
import com.tom.domain.system.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModuleService {
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

    PageInfo findByPage(Integer pageNum, Integer pageSize);

    List<Module> findModulesByRoleId(String roleId);

    void updateRoleModule(String roleId, String moduleIds);

    List<Module> findModulesByUser(User user);
}
