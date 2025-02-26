package com.zio.example.consumer;

import com.zio.example.common.model.User;
import com.zio.example.common.service.UserService;
import com.zio.ziorpc.config.RpcConfig;
import com.zio.ziorpc.proxy.ServiceProxyFactory;
import com.zio.ziorpc.utils.ConfigUtils;

/*
 * 服务消费者示例
 *
 * @author Zio1037
 * @date 2025-02-22 15:33
 */
public class ConsumerExample {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            // 获取代理
            UserService userService = ServiceProxyFactory.getProxy(UserService.class);
            User user = new User();
            user.setName("Zio");
            // 调用
            User newUser = userService.getUser(user);
            if (newUser != null) {
                System.out.println(newUser.getName());
            } else {
                System.out.println("user == null");
            }
            short number = userService.getNumber();
            System.out.println(number);

            System.out.printf("完成第 %d 次调用.\n", i + 1);

//            Thread.sleep(6 * 1000L);
        }
    }
}
