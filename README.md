# RPC by Zio1037

>   先完成，再完美。  

  



## :books: 项目介绍

从0到1实现RPC框架，并在简易版RPC框架的基础上加以改进。  

  



## :speech_balloon: 源码目录

- example-common: 示例代码公用模块
- example-consumer: 示例服务消费者
- example-provider: 示例服务提供者
- zio-rpc-core: 扩展版RPC框架
- zio-rpc-easy: 简易版RPC框架
- Note：项目笔记
- Tutorial：超详细的实现教程  

  



## :heavy_check_mark: 已完成部分

-   简易版RPC框架，支持一行代码调用其它系统上的服务
-   全局配置加载
-   接口Mock
-   多种序列化器实现：Json、Kryo、Hessian
-   SPI机制
-   简易注册中心

  



## :pencil: Todo List

-   [ ] Web服务器多种技术实现
    -   [x] Vert.X
    -   [ ] Socket
    -   [ ] Netty
-   [ ] 注册中心的多种技术实现
    -   [x] Etcd
    -   [ ] ZooKeeper
    -   [ ] Redis
-   [ ] 注册中心优化
    -   [ ] 心跳检测和续期机制
    -   [ ] 服务节点下线机制
    -   [ ] 消费端服务缓存
    -   [ ] 消费端缓存更新
-   [ ] 自定义协议
-   [ ] 消费者优化
    -   [ ] 负载均衡
    -   [ ] 重试机制
    -   [ ] 容错机制
-   [ ] Spring Boot Starter 注解驱动实现  