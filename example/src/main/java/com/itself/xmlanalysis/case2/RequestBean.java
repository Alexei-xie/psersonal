package com.itself.xmlanalysis.case2;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "soap-env:Envelope")//只要字段中有XmlElement注解就可以不适用这个注解
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(propOrder = {"Body"})//此注解指定有哪些属性，需要跟字段属性保持一致，不用写入下面的链接地址属性
public class RequestBean {

    @XmlAttribute(name="xmlns:soap-env")
    protected String soapenv="http://schemas.xmlsoap.org/soap/envelope/";

    @XmlAttribute(name="xmlns:soap")
    protected String soap="http://schemas.xmlsoap.org/wsdl/soap/";

    @XmlAttribute(name="xmlns:xsi")
    protected String xsi="http://www.w3.org/2001/XMLSchema-instance";

    @XmlElement(name="soap-env:Body")//这个注解确定xml标签头，跟下面的属性和XmlType注解中叫什么名字没关系
    protected BodyBean Body;
}



