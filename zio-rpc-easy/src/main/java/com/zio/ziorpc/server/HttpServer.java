package com.zio.ziorpc.server;

/**
 *  HTTP 服务器接口
 *
 * @author Zio1037
 * @date 2025-02-21 16:45
 */
public interface HttpServer {

    /**
     * 启动服务器
     *
     * @param port
     */
    void doStart(int port);
}
