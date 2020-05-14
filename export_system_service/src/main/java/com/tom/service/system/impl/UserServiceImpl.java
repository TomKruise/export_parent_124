package com.tom.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tom.dao.system.UserDao;
import com.tom.domain.system.Role;
import com.tom.domain.system.User;
import com.tom.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll(String companyId) {
        return userDao.findAll(companyId);
    }

    @Override
    public User findById(String userId) {
        return userDao.findById(userId);
    }

    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());

        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(String userId) {
        userDao.delete(userId);
    }

    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, String companyId) {
        PageHelper.startPage(pageNum, pageSize);

        List<User> list = userDao.findAll(companyId);
        return new PageInfo(list);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
