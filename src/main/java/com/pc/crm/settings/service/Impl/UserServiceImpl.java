package com.pc.crm.settings.service.Impl;


import com.pc.crm.exception.LoginException;
import com.pc.crm.settings.dao.UserDao;
import com.pc.crm.settings.domain.User;
import com.pc.crm.settings.service.UserService;
import com.pc.crm.utils.DateTimeUtil;
import com.pc.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 彭聪
 * @date : 2020-08-05 12:50
 **/
public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String, String> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        User user = userDao.login(map);
        if (user == null) {
            throw new LoginException("账号密码错误！");
        }
        String expertime = user.getExpireTime();
        String currentime = DateTimeUtil.getSysTime();
        if (expertime.compareTo(currentime) < 0) {
            throw new LoginException("账号已失效！");
        }


        String lockState = user.getLockState();
        if ("0".equals(lockState)) {
            throw new LoginException("账号已锁定");
        }

        String allowIp = user.getAllowIps();
        if (!allowIp.contains(ip)) {
            throw new
                    LoginException("ip地址受限");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {

        List<User> userList=userDao.getUserList();
        return userList;
    }
}
