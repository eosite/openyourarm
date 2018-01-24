package com.glaf.base.ws;

import javax.xml.ws.Endpoint;

public class UserServicePublish {

	public static void main(String[] args) {
		System.out.println("web service start");
		System.setProperty("config.path", ".");
		UserWebServiceImpl implementor = new UserWebServiceImpl();
		String address = "http://127.0.0.1:8080/glaf/ws/wsUserService";
		Endpoint.publish(address, implementor);
		System.out.println("web service started");
	}

}
