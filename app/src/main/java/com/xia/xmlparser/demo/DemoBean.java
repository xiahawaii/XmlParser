package com.xia.xmlparser.demo;

/**
 * author xiaweiyao
 * create 16/10/11
 * mail 362613381@qq.com
 */
public class DemoBean {

    private String name;
    private String age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public final static String TEST_DATA =
            "<DemoBean>" +
            "   <name>xia</name>" +
            "   <age>18</age>" +
            "   <sex>男</sex>" +
            "</DemoBean>";


    public final static String TEST_DATA_LIST =
            "<data>" +
            "   <DemoBean>" +
            "       <name>xia</name>" +
            "       <age>18</age>" +
            "       <sex>男</sex>" +
            "   </DemoBean>" +
            "   <DemoBean>" +
            "       <name>wei</name>" +
            "       <age>19</age>" +
            "       <sex>男</sex>" +
            "   </DemoBean>" +
            "   <DemoBean>" +
            "       <name>yao</name>" +
            "       <age>20</age>" +
            "       <sex>男</sex>" +
            "   </DemoBean>" +
            "</data>";
}
