package com.byau.service.impl;

import com.byau.dao.IUserDao;
import com.byau.domain.User;
import com.byau.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserDao userDao;

    public User findUser(String username) {
        return this.userDao.findUser(username);
    }

    public boolean loginIn(User user) {
        User loguser = this.userDao.loginIn(user);
        return loguser!=null;
    }
}