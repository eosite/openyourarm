package com.glaf.mqdata.web.dubbo.server;


import com.alibaba.dubbo.config.annotation.Service;

@Service(version="1.0.0",protocol={"rmi","dubbo"})
public class TestServiceImpl implements TestService{
	public void sendMsg(String msg) {
		// TODO Auto-generated method stub
		System.out.println("啊哈哈哈哈哈哈");
	}
}
