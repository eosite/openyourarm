package com.glaf.mqdata.web.dubbo.server;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Service;
import com.glaf.core.util.ResponseUtils;

@Service(version="2.0.0",protocol = {"rmi","rest"})
@Path("/rs/test")
public class TestRestServiceImpl  implements TestRestService{
	@GET
	@POST
	@Path("/sendMsg")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] sendMsg2(String msg) {
		// TODO Auto-generated method stub
		System.out.println("啊哈哈哈哈哈哈");
		return ResponseUtils.responseJsonResult(true);
	}

}