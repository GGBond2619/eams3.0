package com.byau.service;

import com.byau.domain.User;

public interface IUserService {
    User findUser(String paramString);

    boolean loginIn(User paramUser);
}
