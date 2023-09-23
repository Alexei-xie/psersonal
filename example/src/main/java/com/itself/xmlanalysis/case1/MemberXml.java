package com.itself.xmlanalysis.case1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "toUserName",
        "fromUserName",
        "createTime",
        "msgType",
        "event",
        "changeType",
        "userID",
        "name",
        "department",
        "mainDepartment",
        "isLeaderInDept",
        "directLeader",
        "position",
        "mobile",
        "gender",
        "email",
        "bizMail",
        "status",
        "avatar",
        "alias",
        "telephone",
        "address",
        "items",
})
public class MemberXml {
    @XmlElement(name = "ToUserName")
    private String toUserName;
    @XmlElement(name = "FromUserName")
    private String fromUserName;
    @XmlElement(name = "CreateTime")
    private String createTime;
    @XmlElement(name = "MsgType")
    private String msgType;
    @XmlElement(name = "Event")
    private String event;
    @XmlElement(name = "ChangeType")
    private String changeType;
    @XmlElement(name = "UserID")
    private String userID;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Department")
    private String department;
    @XmlElement(name = "MainDepartment")
    private String mainDepartment;
    @XmlElement(name = "IsLeaderInDept")
    private String isLeaderInDept;
    @XmlElement(name = "DirectLeader")
    private String directLeader;
    @XmlElement(name = "Position")
    private String position;
    @XmlElement(name = "Mobile")
    private String mobile;
    @XmlElement(name = "Gender")
    private String gender;
    @XmlElement(name = "Email")
    private String email;
    @XmlElement(name = "BizMail")
    private String bizMail;
    @XmlElement(name = "Status")
    private String status;
    @XmlElement(name = "Avatar")
    private String avatar;
    @XmlElement(name = "Alias")
    private String alias;
    @XmlElement(name = "Telephone")
    private String telephone;
    @XmlElement(name = "Address")
    private String address;
    @XmlElementWrapper(name = "ExtAttr")
    @XmlElement(name = "Item")
    private List<Item> items;

}

