package com.zio.example.provider;

import cn.hutool.core.net.NetUtil;
import com.zio.example.common.service.UserService;
import com.zio.ziorpc.RpcApplication;
import com.zio.ziorpc.config.RegistryConfig;
import com.zio.ziorpc.config.RpcConfig;
import com.zio.ziorpc.model.ServiceMetaInfo;
import com.zio.ziorpc.registry.EtcdRegistry;
import com.zio.ziorpc.registry.LocalRegistry;
import com.zio.ziorpc.registry.Registry;
import com.zio.ziorpc.registry.RegistryFactory;
import com.zio.ziorpc.server.HttpServer;
import com.zio.ziorpc.server.VertxHttpServer;

/**
 * 服务提供者示例
 *
 * @author Zio1037
 * @date 2025-02-24 21:34
 */
public class ProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(rpcConfig.getServerPort());
    }
}

