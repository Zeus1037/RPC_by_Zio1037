## 简易版RPC框架

#### 模块介绍

| **功能**                             | **核心组件**                                                 |
| ------------------------------------ | ------------------------------------------------------------ |
| Web 服务器                           | 使用 **Vert.x**，可选 **Tomcat** 或 **Netty**                |
| 本地服务注册器                       | `LocalRegistry` 基于 `ConcurrentHashMap` （`key` 为服务名称 + `value` 为服务实现类全类名） |
| 通信请求实体类                       | `RpcRequest` 和 `RpcResponse`                                |
| `RpcRequest` 请求消息体，支持序列化  | 请求服务名 `serviceName` <br />方法名 `methodName` <br />方法参数类型 `paramTypes` <br />传入实参 `params` |
| `RpcResponse` 响应消息体，支持序列化 | 响应数据 `data` <br />响应数据类型 `datatype` <br />响应信息 `msg` <br />异常信息 `exception` |
| 序列化器                             | `JdkSerializer`，基于 JDK 原生序列化方式                     |
| 请求处理器                           | `HttpServerHandler` 借助序列化器反序列化 HTTP 请求，调用本地服务注册/序列化返回响应。 |
| 动态代理处理器                       | `ServiceProxyFactory`返回 `ServiceProxy`实例，实现透明调用   |

