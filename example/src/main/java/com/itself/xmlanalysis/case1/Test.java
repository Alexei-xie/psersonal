package com.itself.xmlanalysis.case1;

import com.google.common.collect.Lists;
import com.itself.utils.XmlAnalysisUtil;

/**
 * Bean与xml互转
 * xml 转 Bean
 * @Author xxw
 * @Date 2023/03/22
 */
public class Test {
    public static void main(String[] args) {
        String xml = "<xml>\n" +
                "\t<ToUserName>toUser</ToUserName>\n" +
                "\t<FromUserName>sys</FromUserName> \n" +
                "\t<CreateTime>1403610513</CreateTime>\n" +
                "\t<MsgType><![CDATA[event]]></MsgType>\n" +
                "\t<Event><![CDATA[change_contact]]></Event>\n" +
                "\t<ChangeType>create_user</ChangeType>\n" +
                "\t<UserID><![CDATA[zhangsan]]></UserID>\n" +
                "\t<Name><![CDATA[张三]]></Name>\n" +
                "\t<Department><![CDATA[1,2,3]]></Department>\n" +
                "\t<MainDepartment>1</MainDepartment>\n" +
                "\t<IsLeaderInDept><![CDATA[1,0,0]]></IsLeaderInDept>\n" +
                "\t<DirectLeader><![CDATA[lisi,wangwu]]></DirectLeader>\n" +
                "\t<Position><![CDATA[产品经理]]></Position>\n" +
                "\t<Mobile>13800000000</Mobile>\n" +
                "\t<Gender>1</Gender>\n" +
                "\t<Email><![CDATA[zhangsan@gzdev.com]]></Email>\n" +
                "\t<BizMail><![CDATA[zhangsan@qyycs2.wecom.work]]></BizMail>\n" +
                "\t<Status>1</Status>\n" +
                "\t<Avatar><![CDATA[http://wx.qlogo.cn/mmopen/ajNVd]]>\n" +
                "        </Avatar>\n" +
                "\t<Alias><![CDATA[zhangsan]]></Alias>\n" +
                "\t<Telephone><![CDATA[020-123456]]></Telephone>\n" +
                "\t<Address><![CDATA[广州市]]></Address>\n" +
                "\t<ExtAttr>\n" +
                "\t\t<Item>\n" +
                "\t\t<Name><![CDATA[爱好]]></Name>\n" +
                "\t\t<Type>0</Type>\n" +
                "\t\t<Text>\n" +
                "\t\t\t<Value><![CDATA[旅游]]></Value>\n" +
                "\t\t</Text>\n" +
                "\t\t</Item>\n" +
                "\t\t<Item>\n" +
                "\t\t<Name><![CDATA[卡号]]></Name>\n" +
                "\t\t<Type>1</Type>\n" +
                "\t\t<Web>\n" +
                "\t\t\t<Title><![CDATA[企业微信]]></Title>\n" +
                "\t\t\t<Url><![CDATA[https://work.weixin.qq.com]]></Url>\n" +
                "\t\t</Web>\n" +
                "\t\t</Item>\n" +
                "\t</ExtAttr>\n" +
                "</xml>";
        MemberXml bean = XmlAnalysisUtil.convertToJavaBean(xml, MemberXml.class);
        System.out.println(bean);
        TextValue textValue = new TextValue("textValue");
        WebValue webValue = new WebValue("title", "test_url");
        Item item = new Item("type", "name", textValue, webValue);
        Item item1 = new Item("type1", "name1", textValue, webValue);
        MemberXml memberXml = new MemberXml();
        memberXml.setAddress("address");
        memberXml.setEmail("email");
        memberXml.setItems(Lists.newArrayList(item1,item));
        System.out.println(XmlAnalysisUtil.convertToXml(memberXml,"UTF-8"));
    }

}
