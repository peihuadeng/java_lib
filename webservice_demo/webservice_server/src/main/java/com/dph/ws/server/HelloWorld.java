package com.dph.ws.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface HelloWorld {
	
	@WebMethod
	public @WebResult String hello(@WebParam String name);

}
