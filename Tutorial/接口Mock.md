

## 需求分析

#### 什么是Mock

RPC框架的核心功能是调用其他远程服务。但是在实际开发和测试过程中，有时可能无法直接访问真实的远程服务，或者访问真实的远程服务可能会产生不可控的影响，例如网络延迟、服务不稳定等。在这种情况下，就需要使用Mock服务来模拟远程服务的行为，以便进行接口的测试、开发和调试。

Mock是指模拟对象，通常用于测试代码中，特别是在单元测试中，便于我们跑通业务流程。

#### 为什么要支持Mock

虽然Mock服务并不是RPC框架的核心能力，但是它的开发成本并不高。而且给RPC框架支持Mock后，开发者就可以轻松调用服务接口、跑通业务流程，不必依赖真实的远程服务，提高使用体验，何乐而不为呢？

我们希望能够用最简单的方式，比如一个配置，就让开发者使用mock服务。

  




## 设计方案

如何创建模拟对象呢？

在构建简易版RPC框架时，我们就提到了一种动态创建对象的方法一一动态代理。之前是通过动态代理创建远程调用对象。同理，我们可以通过动态代理创建一个**调用方法时返回固定值**的对象。

  



## 开发实现

1.   我们可以支持开发者通过修改配置文件的方式开启mock，首先给全局配置类`RpcConfig`新增mock字段，默认值为false。

![image-20250223194918332](..\assets\image-20250223194918332.png)

2.   在Proxy包下新增`MockServiceProxy`类，用于生成mock代理服务。在这个类中，需要提供一个根据服务接口类型返回固定值的方法。

![image-20250223195152829](..\assets\image-20250223195152829.png)

在上述代码中，通过`getDefaultobject`方法，根据代理接口的`class`返回不同的默认值，比如针对boolean类型返回false、对象类型返回`null`等。

3.   `ServiceProxyFactory`服务代理工厂新增获取mock代理对象的方法`getMockProxy`。可以通过读取已定义的全局配置 `mock` 来区分创建哪种代理对象。

修改`ServiceProxyFactory`，完整代码如下：

![image-20250223195521850](..\assets\image-20250223195521850.png)

  



## 测试

1.   在`example-common`模块的`UserService`中写个具有默认实现的新方法。之后需要调用该方法来测试mock代理服务是否生效，即查看调用的是模拟服务还是真实服务。

![image-20250223200025539](..\assets\image-20250223200025539.png)

2.   修改示例服务消费者模块中的`application.properties`配置文件，将mock设置为true：

![image-20250223200137063](..\assets\image-20250223200137063.png)

3.   修改`ConsumerExample`类，编写调用`userService.getNumber`的测试代码

![image-20250223200822822](..\assets\image-20250223200822822.png)

4.   DEBUG模式运行`EasyProviderExample`类和`ConsumerExample`类（运行前确保`example-provider`类下的`application.properties`配置文件中的端口号与`ServiceProxy`类中发起http请求访问的端口号一致）

![image-20250223201738177](..\assets\image-20250223201738177.png)

输出的结果值为0，说明调用了`MockServiceProxy`模拟服务代理。

如果修改mock为false，可以看到输出为1，说明此时调用的是默认实现的真实服务。

![image-20250223201818609](..\assets\image-20250223201818609.png)

  



## Todo List

-   [ ] 完善Mock的逻辑，支持更多返回类型的默认值生成。
    -   [ ] 可能方案：使用Faker之类的伪造数据生成库来生成默认值。

