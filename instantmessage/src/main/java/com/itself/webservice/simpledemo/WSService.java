package com.itself.webservice.simpledemo;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
  * 这是webService的接口
  */
 @WebService
 public interface WSService {
   @WebMethod
   String sayHello(String name);
 }