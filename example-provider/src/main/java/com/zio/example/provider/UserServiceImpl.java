package com.zio.example.provider;

import com.zio.example.common.model.User;
import com.zio.example.common.service.UserService;

/**
 * @author Zio1037
 * @date 2025-02-21 16:09
 */
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("Consumer sends: " + user.getName());
        // 将用户名反转后返回
        user.setName(new StringBuffer(user.getName()).reverse().toString());
        return user;
    }
}

