package com.zio.ziorpc.fault.tolerant;

/**
 * 容错策略键名常量
 *
 * @author Zio1037
 * @date 2025-02-26 21:32
 */
public interface TolerantStrategyKeys {

    /**
     * 快速失败
     */
    String FAIL_FAST = "failFast";

    /**
     * 静默处理
     */
    String FAIL_SAFE = "failSafe";

    /**
     * 故障转移
     */
    String FAIL_OVER = "failOver";

}

