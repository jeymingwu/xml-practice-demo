package com.example.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : jeymingwu
 * @date : 2021-05-31
 **/

public class SAXParseHandler<T> extends DefaultHandler {

    private int index;
    private String value;

    private String rootName;
    private String elementName;
    private String className;
    private List<T> list;

    private Object object;

    public SAXParseHandler(String rootName, String elementName, String className) {
        index = 0;
        value = null;
        this.rootName = rootName;
        this.elementName = elementName;
        this.className = className;
        this.list = new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }

    /**
     * SAX 解析开始标记
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("================SAX解析开始标记================");
    }

    /**
     * SAX 解析结束标记
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("================SAX解析结束标记================");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (qName.equals(this.elementName)) {
            ++this.index;
            try {
                this.object = Class.forName(this.className).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("\n=========开始遍历第 " + this.index + " 个元素的内容=========");
            for (int i = 0; i < attributes.getLength(); ++i) {
                System.out.println("属性名：" + attributes.getQName(i) + " 属性值：" + attributes.getValue(i));
                try {
                    this.setValue(attributes.getQName(i), attributes.getValue(i));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else if (!qName.equals(this.rootName)) {
            System.out.print("节点名为：" + qName);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (qName.equals(this.elementName)) {
            this.list.add((T) this.object);
//            this.object = null;
            System.out.println("=========结束遍历第 " + this.index + " 个元素的内容=========");
        } else {
            try {
                this.setValue(qName, this.value);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 遍历子节点的值
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        value = new String(ch, start, length);
        if (!value.trim().equals("")) {
            System.out.print("\t节点值：" + value + "\n");
        }
    }

    public void setValue(String name, String value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field:fields) {

            if (field.getName().equals(name)) {
                String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = this.object.getClass().getMethod(methodName, String.class);

                if (value == null) {
                    method.invoke(this.object, this.value);
                } else {
                    method.invoke(this.object, value);
                }
            }

        }

    }
}
