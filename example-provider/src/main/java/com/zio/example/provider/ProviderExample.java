package com.zio.example.provider;

import cn.hutool.core.net.NetUtil;
import com.github.rholder.retry.RetryException;
import com.zio.example.common.service.UserService;
import com.zio.ziorpc.RpcApplication;
import com.zio.ziorpc.config.RegistryConfig;
import com.zio.ziorpc.config.RpcConfig;
import com.zio.ziorpc.fault.retry.FixedIntervalRetryStrategy;
import com.zio.ziorpc.fault.retry.RetryStrategy;
import com.zio.ziorpc.fault.retry.RetryStrategyFactory;
import com.zio.ziorpc.model.ServiceMetaInfo;
import com.zio.ziorpc.registry.EtcdRegistry;
import com.zio.ziorpc.registry.LocalRegistry;
import com.zio.ziorpc.registry.Registry;
import com.zio.ziorpc.registry.RegistryFactory;
import com.zio.ziorpc.server.HttpServer;
import com.zio.ziorpc.server.VertxHttpServer;
import com.zio.ziorpc.server.tcp.VertxTcpClient;
import com.zio.ziorpc.server.tcp.VertxTcpServer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 服务提供者示例
 *
 * @author Zio1037
 * @date 2025-02-24 21:34
 */
public class ProviderExample {

    public static void main(String[] args) {
        Logger.getLogger("io.grpc").setLevel(Level.INFO);
        // RPC 框架初始化
        RpcApplication.init();
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务到本地
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 启动 TCP 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        try {
            vertxTcpServer.doStart(rpcConfig.getServerPort());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // 注册服务到注册中心
        // 考虑网络波动导致的注册失败情况，当注册失败时进行重试
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            new FixedIntervalRetryStrategy().doRetry4Registry(() ->
                    registry.register(serviceMetaInfo)
            );
            System.out.println("Registered successfully");
        } catch (RetryException e) {
            System.err.println("重试次数达到阈值，服务注册失败");
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("未知异常：" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}