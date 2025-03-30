package com.zio.ziorpc.config;


import com.zio.ziorpc.registry.RegistryKeys;
import lombok.Data;

/**
 * RPC 框架注册中心配置
 *
 * @author Zio1037
 * @date 2025-02-24 20:33
 */
@Data
public class RegistryConfig {

    /**
     * 注册中心类别
     */
    private String registry = RegistryKeys.ETCD;

    /**
     * 注册中心所有节点的地址
     */
    private String[] addressList = {
            "http://localhost:2379"
    };

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间（单位毫秒）
     */
    private Long timeout = 10000L;
}
