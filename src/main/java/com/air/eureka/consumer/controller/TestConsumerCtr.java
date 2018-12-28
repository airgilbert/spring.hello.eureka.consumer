package com.air.eureka.consumer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestConsumerCtr {
	@Autowired
	public RestTemplate restTemplate;
	@Value("${dubbo.registry.address}")
	public String dubboAddress;

	@GetMapping(value = "/gotoUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> getUser(@RequestParam Integer id) {
		Map<String, Object> data = new HashMap<>();
		/**
		 * 小伙伴发现没有，地址居然是http://service-provider 居然不是http://127.0.0.1:8082/
		 * 因为他向注册中心注册了服务，服务名称service-provider,我们访问service-provider即可
		 */
		data = restTemplate.getForObject("http://hello-provider/getUser?id=" + id, Map.class);
		data.put("dubboAddress", dubboAddress);
		return data;
	}

}
