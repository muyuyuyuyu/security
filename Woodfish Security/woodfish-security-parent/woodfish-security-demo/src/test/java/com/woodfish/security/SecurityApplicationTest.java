package com.woodfish.security;

// 静态导入方便测试
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.social.support.ParameterMap;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityApplicationTest {



    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testHttpRequest() throws Exception {
        mockMvc.perform(get("/user") // 请求的地址 get表示HTTP请求类型
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // 请求数据的格式
                .andExpect(status().isOk()) // 期望结果匹配的状态码
                .andExpect(jsonPath("$.length()").value(3));
    }
    @Test
    public void testListUser() throws Exception {
        System.out.println(returnJsonStringSuccess("/user"));
    }

    @Test
    public void testRequestWithParams() throws Exception {
        MultiValueMap<String,String> paramsMap = new LinkedMultiValueMap<String,String>();
        paramsMap.add("username", "张三");
        paramsMap.add("password", "123456");
        System.out.println(returnJsonWithParams("/user/login"));
    }

    private String returnJsonStringSuccess(String url) throws Exception {
        return mockMvc.perform(get(url)// 请求的地址 get表示HTTP请求类型
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // 请求数据的格式
                .andExpect(status().isOk()) // 期望结果匹配的状态码
                .andReturn().getResponse().getContentAsString();
    }

    private String returnJsonStringFail(String url) throws Exception {
        return mockMvc.perform(get(url) // 请求的地址 get表示HTTP请求类型
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // 请求数据的格式
                .andExpect(status().is4xxClientError()) // 期望结果匹配的状态码
                .andReturn().getResponse().getContentAsString();
    }

    private String returnJsonWithParams(String url, MultiValueMap<String,String> paramsMap) throws Exception {
        return mockMvc.perform(post(url).params(paramsMap) // 请求的地址 get表示HTTP请求类型
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // 请求数据的格式
                .andExpect(status().isOk()) // 期望结果匹配的状态码
                .andReturn().getResponse().getContentAsString();
    }
    // post请求只能使用content @RequestBody只接受Json串
    private String returnJsonWithParams(String url) throws Exception {
        String content = "{\"username\":\"zhangsan\",\"password\":\"123456\"}";

        return mockMvc.perform(post(url).content(content)// 请求的地址 get表示HTTP请求类型
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // 请求数据的格式
                .andExpect(status().isOk()) // 期望结果匹配的状态码
                .andReturn().getResponse().getContentAsString();
    }
}
