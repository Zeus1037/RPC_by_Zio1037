package com.zio.ziorpc.loadbalancer;

/**
 * 负载均衡器键名常量
 *
 * @author Zio1037
 * @date 2025-02-26 15:27
 */
public interface LoadBalancerKeys {

    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";

}

