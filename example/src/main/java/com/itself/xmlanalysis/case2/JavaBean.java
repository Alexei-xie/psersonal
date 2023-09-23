package com.itself.xmlanalysis.case2;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "jns0:call")
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(propOrder = {"HH", "BZMC", "LXR", "SJHM", "SFZBH","DBR", "DBRSFZBH", "DBRSJHM", "XYDM", "BDCZ","DYH", "MS1", "MS2"})
public class JavaBean {

    @XmlElement(name = "HH")
    private String HH;
    @XmlElement(name = "BZMC")
    private String BZMC;
    @XmlElement(name = "LXR")
    private String LXR;
    @XmlElement(name = "SJHM")
    private String SJHM;
    @XmlElement(name = "SFZBH")
    private String SFZBH;
    @XmlElement(name = "DBR")
    private String DBR;
    @XmlElement(name = "DBRSFZBH")
    private String DBRSFZBH;
    @XmlElement(name = "DBRSJHM")
    private String DBRSJHM;
    @XmlElement(name = "XYDM")
    private String XYDM;
    @XmlElement(name = "BDCZ")
    private String BDCZ;
    @XmlElement(name = "DYH")
    private String DYH;
    @XmlElement(name = "MS1")
    private String MS1;
    @XmlElement(name = "MS2")
    private String MS2;
}