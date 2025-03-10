package com.zio.ziorpc.loadbalancer;

import com.zio.ziorpc.model.ServiceMetaInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 负载均衡器测试
 *
 * @author Zio1037
 * @date 2025-02-26 15:44
 */
public class LoadBalancerTest {

    final LoadBalancer loadBalancer = new ConsistentHashLoadBalancer();

    @Test
    public void select() {
        // 请求参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", "apple");
        // 服务列表
        ServiceMetaInfo serviceMetaInfo1 = new ServiceMetaInfo();
        serviceMetaInfo1.setServiceName("myService");
        serviceMetaInfo1.setServiceVersion("1.0");
        serviceMetaInfo1.setServiceHost("localhost");
        serviceMetaInfo1.setServicePort(1234);
        ServiceMetaInfo serviceMetaInfo2 = new ServiceMetaInfo();
        serviceMetaInfo2.setServiceName("myService");
        serviceMetaInfo2.setServiceVersion("1.0");
        serviceMetaInfo2.setServiceHost("zio.com");
        serviceMetaInfo2.setServicePort(80);
        ServiceMetaInfo serviceMetaInfo3 = new ServiceMetaInfo();
        serviceMetaInfo3.setServiceName("myService");
        serviceMetaInfo3.setServiceVersion("1.0");
        serviceMetaInfo3.setServiceHost("rpc.org");
        serviceMetaInfo3.setServicePort(1037);
        List<ServiceMetaInfo> serviceMetaInfoList = Arrays.asList(serviceMetaInfo1, serviceMetaInfo2, serviceMetaInfo3);

        // 连续调用 10 次
        ServiceMetaInfo serviceMetaInfo;
        for (int i = 0; i < 10; i++) {
            serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
            System.out.println(serviceMetaInfo);
            Assert.assertNotNull(serviceMetaInfo);
        }
    }
}
