package com.zio.ziorpc.config;

import lombok.Data;

/**
 * RPC 框架配置
 *
 * @author Zio1037
 * @date 2025-02-22 15:12
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "zio-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private boolean mock = false;
}
