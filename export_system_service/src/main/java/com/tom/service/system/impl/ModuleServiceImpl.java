package com.tom.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tom.dao.system.ModuleDao;
import com.tom.domain.system.Module;
import com.tom.domain.system.User;
import com.tom.service.system.ModuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;
    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    @Override
    public Module findById(String id) {
        return moduleDao.findById(id);
    }

    @Override
    public void save(Module module) {
        module.setId(UUID.randomUUID().toString());
        moduleDao.save(module);
    }

    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    @Override
    public void delete(String id) {
        moduleDao.delete(id);
    }

    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Module> list = moduleDao.findAll();
        return new PageInfo(list);
    }

    @Override
    public List<Module> findModulesByRoleId(String roleId) {
        return moduleDao.findModulesByRoleId(roleId);
    }

    @Override
    public void updateRoleModule(String roleId, String moduleIds) {
        moduleDao.deleteModulesByRoleId(roleId);

        if (StringUtils.isNotEmpty(moduleIds)) {
            String[] modules = moduleIds.split(",");
            for (String moduleId : modules) {
                moduleDao.saveRoleModule(roleId, moduleId);
            }
        }

    }

    @Override
    public List<Module> findModulesByUser(User user) {
        Integer degree = user.getDegree();

        if (degree == 0) {
            return moduleDao.findByBelong(0);
        } else if (degree == 1) {
            return moduleDao.findByBelong(1);
        } else {
            return moduleDao.findByUserId(user.getId());
        }
    }
}
