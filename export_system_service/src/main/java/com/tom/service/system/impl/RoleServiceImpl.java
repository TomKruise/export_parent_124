package com.tom.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tom.dao.system.RoleDao;
import com.tom.domain.system.Role;
import com.tom.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    @Override
    public void save(Role role) {
        role.setId(UUID.randomUUID().toString());
        roleDao.save(role);
    }

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public void delete(String id) {
        roleDao.delete(id);
    }

    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, String companyId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> list = roleDao.findAll(companyId);
        return new PageInfo(list);
    }

    @Override
    public List<Role> findByUserId(String userId) {
        return roleDao.findByUserId(userId);
    }

    @Override
    public void changeRole(String userId, String[] roleIds) {
        roleDao.deleteByUserId(userId);

        if (null != roleIds && roleIds.length > 0) {
            for (String roleId : roleIds) {
                roleDao.saveUserRole(userId, roleId);
            }
        }
    }
}
