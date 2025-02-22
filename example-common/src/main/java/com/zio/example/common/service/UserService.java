package com.zio.example.common.service;

import com.zio.example.common.model.User;

/**
 * @author Zio1037
 * @date 2025-02-21 15:52
 */
public interface UserService {
    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);
}
