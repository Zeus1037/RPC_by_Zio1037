package com.zio.ziorpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zio.ziorpc.RpcApplication;
import com.zio.ziorpc.config.RpcConfig;
import com.zio.ziorpc.constant.RpcConstant;
import com.zio.ziorpc.fault.retry.RetryStrategy;
import com.zio.ziorpc.fault.retry.RetryStrategyFactory;
import com.zio.ziorpc.fault.tolerant.TolerantStrategy;
import com.zio.ziorpc.fault.tolerant.TolerantStrategyFactory;
import com.zio.ziorpc.loadbalancer.LoadBalancer;
import com.zio.ziorpc.loadbalancer.LoadBalancerFactory;
import com.zio.ziorpc.model.RpcRequest;
import com.zio.ziorpc.model.RpcResponse;
import com.zio.ziorpc.model.ServiceMetaInfo;
import com.zio.ziorpc.protocol.*;
import com.zio.ziorpc.registry.Registry;
import com.zio.ziorpc.registry.RegistryFactory;
import com.zio.ziorpc.serializer.JdkSerializer;
import com.zio.ziorpc.serializer.Serializer;
import com.zio.ziorpc.serializer.SerializerFactory;
import com.zio.ziorpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 服务代理（JDK 动态代理）
 *
 * @author Zio1037
 * @date 2025-02-21 17:31
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("暂无服务地址");
        }

        // 负载均衡
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        // 将调用方法名（请求路径）作为负载均衡参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        System.out.println("获取节点：" + selectedServiceMetaInfo);

        // rpc 请求
        // 使用重试机制
        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
        } catch (Exception e) {
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            Map<String, Object> requestTolerantParamMap = new HashMap<>();
            requestTolerantParamMap.put("rpcRequest", rpcRequest);
            requestTolerantParamMap.put("selectedServiceMetaInfo", selectedServiceMetaInfo);
            requestTolerantParamMap.put("serviceMetaInfoList", serviceMetaInfoList);
            rpcResponse = tolerantStrategy.doTolerant(requestTolerantParamMap, e);
        }
        return rpcResponse.getData();
    }

}
