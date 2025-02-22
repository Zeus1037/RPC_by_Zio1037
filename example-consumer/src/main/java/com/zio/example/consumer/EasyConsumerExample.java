package com.zio.example.consumer;

import com.zio.example.common.model.User;
import com.zio.example.common.service.UserService;
import com.zio.ziorpc.proxy.ServiceProxy;
import com.zio.ziorpc.proxy.ServiceProxyFactory;

/**
 * @author Zio1037
 * @date 2025-02-21 16:23
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
//        // 静态代理
//        UserService userService = new UserServiceProxy();

        // 动态代理
        UserService userService = new ServiceProxyFactory().getProxy(UserService.class);

        User user = new User();
        user.setName("Zio");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println("Provider responds: " + newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
