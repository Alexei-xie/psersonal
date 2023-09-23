package com.itself.xmlanalysis.case2;

import com.itself.utils.XmlAnalysisUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;


/**bean转xml
 * todo xml转bean有问题
 * @Author xxw
 * @Date 2023/03/22
 */
public class Test {

    public static void main(String[] args) {

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<soap-env:Envelope xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <soap-env:Body xmlns:jns0='http://com.pft.webserviceintf' >\n" +
                "        <jns0:call>\n" +
                "            <servicename>ZZ39_P_WS_BDCGH</servicename>\n" +
                "            <params>1000111111</params>\n" +
                "            <params>淮北供水</params>\n" +
                "            <params>测试</params>\n" +
                "            <params>15258861987</params>\n" +
                "            <params>420281198910127670</params>\n" +
                "            <params>测试2</params>\n" +
                "            <params>123</params>\n" +
                "            <params>15258861987</params>\n" +
                "            <params>1234</params>\n" +
                "            <params>HB0001</params>\n" +
                "            <params>BDC001</params>\n" +
                "            <params>南黎路198号</params>\n" +
                "            <params>测试3</params>\n" +
                "        </jns0:call>\n" +
                "    </soap-env:Body>\n" +
                "</soap-env:Envelope>";
        RequestBean requestBean = new RequestBean();
        BodyBean bodyBean = new BodyBean();
        JavaBean bean = new JavaBean();
        bean.setHH("111");
        bean.setBZMC("111");
        bean.setDBR("111");
        bean.setLXR("111");
        bean.setSJHM("111");
        bean.setSFZBH("111");
        bean.setDBRSFZBH("111");
        bean.setDBRSJHM("111");
        bean.setBDCZ("111");
        bean.setDYH("111");
        bean.setMS1("111");
        bean.setMS2("111");
        bodyBean.setCall(bean);
        requestBean.setBody(bodyBean);
        System.out.println(getXml(requestBean));
        System.out.println(XmlAnalysisUtil.convertToXml(requestBean,"UTF-8"));
    }

    /**
     * bean对象转换XML格式
     * @param caseBean
     * @return
     */
    public static String getXml(Object caseBean) {
        String xmlObj = "";
        try {
            JAXBContext context = JAXBContext.newInstance(caseBean.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            marshaller.marshal(caseBean, baos);
            xmlObj = new String(baos.toByteArray());
        }catch (Exception e) {
            xmlObj = "";
            // log.error("bean对象转换XML格式失败，原因是：", e);
        }
        return xmlObj;
    }
}
