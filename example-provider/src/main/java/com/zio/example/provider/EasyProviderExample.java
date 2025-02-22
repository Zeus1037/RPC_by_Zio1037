package com.zio.example.provider;

import com.zio.example.common.service.UserService;
import com.zio.ziorpc.registry.LocalRegistry;
import com.zio.ziorpc.server.HttpServer;
import com.zio.ziorpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 *
 * @author Zio1037
 * @date 2025-02-21 16:15
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动web服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}