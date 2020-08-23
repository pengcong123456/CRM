package com.pc.crm.settings.service;

import com.pc.crm.exception.LoginException;
import com.pc.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd,String ip) throws LoginException;

    List<User> getUserList();
}
