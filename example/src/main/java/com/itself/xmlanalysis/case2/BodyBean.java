package com.itself.xmlanalysis.case2;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "soap-env:Body")
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(propOrder = {"call"})
public class BodyBean {

    @XmlAttribute(name="xmlns:jns0")
    protected String jns0="http://com.pft.webserviceintf";

    @XmlElement(name="jns0:call")
    private JavaBean call;
}