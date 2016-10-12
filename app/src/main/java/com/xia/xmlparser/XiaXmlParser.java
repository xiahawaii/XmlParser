package com.xia.xmlparser;

import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * author xiaweiyao
 * create 16/10/11
 * mail 362613381@qq.com
 */
public class XiaXmlParser {

    private final static String TAG = "XiaXmlParser";

    /**
     * 将XML字符,反序列化成对象实例
     * @param xmlStr XML字符
     * @param cls 反序列化对象的Class对象
     * @param <T> 反序列化对象
     * @return 反序列结果
     */
    public static <T> T xmlToSingleObje(String xmlStr, Class<T> cls) {

        // 数据源为空,目标类型为空,不处理
        if (TextUtils.isEmpty(xmlStr) || cls == null)
            return null;

        return XiaXmlParser.xmlToSingleObj(new ByteArrayInputStream(xmlStr.getBytes()), cls);
    }

    /**
     * 将XML流,反序列化成对象实例
     * @param xmlInputStream XML流
     * @param cls 反序列化对象的Class对象
     * @param <T> 反序列化对象
     * @return 反序列结果
     */
    public static <T> T xmlToSingleObj(InputStream xmlInputStream, Class<T> cls) {

        // 数据源为空,目标类型为空,不处理
        if (xmlInputStream == null || cls == null)
            return null;

        // 获取反序列对象的属性,无属性,不执行解析
        Field[] objFields = cls.getDeclaredFields();
        if (objFields == null || objFields.length == 0) {
            closeInputStream(xmlInputStream);
            return null;
        }

        // 开始解析
        try {

            // 声明结果对象
            T obj = null;

            // 构建解析器
            XmlPullParser mParser = Xml.newPullParser();
            mParser.setInput(xmlInputStream, "UTF-8");

            // 循环处理解析事件,直至XML结束
            while (mParser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (mParser.getEventType()) {

                    // 文档开始,实例化结果对象
                    case XmlPullParser.START_DOCUMENT:
                        obj = cls.newInstance();
                        break;

                    // 解析到标签,获取标签名,匹配是否有相同的属性名(忽略大小写),有则赋值
                    case XmlPullParser.START_TAG:
                        String name = mParser.getName();
                        for (Field field : objFields) {
                            if (field.getName().equalsIgnoreCase(name)) {
                                field.setAccessible(true);
                                field.set(obj, mParser.nextText());
                                break;
                            }
                        }
                        break;

                    default:
                        break;
                }

                // 执行下一个事件
                mParser.next();
            }
            closeInputStream(xmlInputStream);
            return obj;
        } catch (Exception e) {
            closeInputStream(xmlInputStream);
            e.printStackTrace();
            Log.e(TAG, "XiaXmlParser.xmlToSingleObj()执行出错");
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * 将XML字符,反序列化成列表对象实例
     * @param xmlStr XML字符
     * @param cls 反序列化对象的Class对象
     * @param childName 列表节点名称
     * @param <T> 反序列化对象
     * @return 解析结果
     */
    public static <T> List<T> xmlToListObj(String xmlStr, Class<T> cls, String childName) {

        // 数据源,目标类型,列表节点名称为空,不处理
        if (TextUtils.isEmpty(xmlStr) || cls == null || TextUtils.isEmpty(childName)) {
            return null;
        }

        return XiaXmlParser.xmlToListObj(new ByteArrayInputStream(xmlStr.getBytes()), cls, childName);
    }

    /**
     * 将XML字符,反序列化成列表对象实例
     * @param xmlInputStream XML输入流
     * @param cls 反序列化对象的Class对象
     * @param childName 列表节点名称
     * @param <T> 反序列化对象
     * @return 解析结果
     */
    public static <T> List<T> xmlToListObj(InputStream xmlInputStream, Class<T> cls, String childName) {

        // 数据源,目标类型,列表节点名称为空,不处理
        if (xmlInputStream == null || cls == null || TextUtils.isEmpty(childName)) {
            return null;
        }

        // 获取反序列对象的属性,无属性,不执行解析
        Field[] objFields = cls.getDeclaredFields();
        if (objFields == null || objFields.length == 0) {
            closeInputStream(xmlInputStream);
            return null;
        }

        // 开始解析
        try {

            // 声明结果对象
            List<T> datas = null;
            T obj = null;

            // 构建解析器
            XmlPullParser mParser = Xml.newPullParser();
            mParser.setInput(xmlInputStream, "UTF-8");

            // 循环处理解析事件,直至XML结束
            while (mParser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (mParser.getEventType()) {

                    // 文档开始,实例化结果对象
                    case XmlPullParser.START_DOCUMENT:
                        datas = new ArrayList<>();
                        break;

                    // 解析到开始标签,获取标签名
                    case XmlPullParser.START_TAG:
                        String name = mParser.getName();

                        // 若是元素节点,创建元素
                        if (name.equalsIgnoreCase(childName)) {
                            obj = cls.newInstance();
                            break;
                        }

                        // 匹配是否有相同的属性名(忽略大小写),有则赋值
                        for (Field field : objFields) {
                            if (field.getName().equalsIgnoreCase(name)) {
                                field.setAccessible(true);
                                field.set(obj, mParser.nextText());
                                break;
                            }
                        }
                        break;

                    // 解析到结束标签,获取标签名
                    case XmlPullParser.END_TAG:
                        String endTag = mParser.getName();

                        // 若是元素节点,结束当前元素的解析,放入列表
                        if (endTag.equalsIgnoreCase(childName) && datas != null && obj != null) {
                            datas.add(obj);
                        }
                        break;

                    default:
                        break;
                }

                // 执行下一个事件
                mParser.next();
            }
            closeInputStream(xmlInputStream);
            return datas;
        } catch (Exception e) {
            closeInputStream(xmlInputStream);
            e.printStackTrace();
            Log.e(TAG, "XiaXmlParser.xmlToListObj()执行出错");
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * 关闭输入流
     * @param xmlInputStream 输入流
     */
    private static void closeInputStream(InputStream xmlInputStream) {
        try {
            xmlInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
