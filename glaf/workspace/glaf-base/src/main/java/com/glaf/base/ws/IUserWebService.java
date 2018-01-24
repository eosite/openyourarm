package com.glaf.base.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IUserWebService {

	String getUserJson(@WebParam(name = "userId") String userId);

}
