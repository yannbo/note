package com.cisco.webex.joinserver.service.join.mjsdocshow.engine.beancreator;

import java.util.Arrays;
import java.util.List;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * The demo is to mock private method, all steps are necessary. @1@2@3@4
 *
 * <p>如果你的测试用例里没有使用注解 @PrepareForTest，那么可以不用加注解 @RunWith(PowerMockRunner.class)，反之亦然。<p/>
 * <p>当你需要使用PowerMock强大功能(Mock静态、final、私有方法等)的时候，就需要加注解 @PrepareForTest。<p/>
 */

@RunWith(PowerMockRunner.class)                                                    //1
@PrepareForTest({BusinessService.class})                                           //2
public class BusinessServiceTest {
    @Test
    public void test() throws Exception {
        List<String> expected = Arrays.asList("test", "test2");
        // Mock 私有方法
        BusinessService businessService = PowerMockito.spy(new BusinessService());  //3
        PowerMockito.doReturn(expected).when(businessService, "users");             //4
        // 调用测试方法
        List<String> users = businessService.getAllUser();
        // 验证
        Assert.assertArrayEquals(expected.toArray(), users.toArray());
    }
}
class BusinessService {
    public List<String> getAllUser() {
        // 调用 private 方法
        return users();
    }
    private List<String> users() {
        return Arrays.asList("zhangsan", "lisi");
    }
}
