package com.zio.example.consumer;

import com.zio.ziorpc.config.RpcConfig;
import com.zio.ziorpc.utils.ConfigUtils;

/**
 * 服务消费者示例
 *
 * @author Zio1037
 * @date 2025-02-22 15:33
 */
public class ConsumerExample {

    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
