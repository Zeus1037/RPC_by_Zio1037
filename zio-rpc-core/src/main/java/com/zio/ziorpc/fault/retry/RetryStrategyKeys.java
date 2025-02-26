package com.zio.ziorpc.fault.retry;

/**
 * 重试策略键名常量
 *
 * @author Zio1037
 * @date 2025-02-26 20:15
 */
public interface RetryStrategyKeys {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";

}

