package com.zio.ziorpc.fault.tolerant;

import com.zio.ziorpc.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略
 *
 * @author Zio1037
 * @date 2025-02-26 21:26
 */
public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context 上下文，用于传递数据
     * @param e       异常
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}

