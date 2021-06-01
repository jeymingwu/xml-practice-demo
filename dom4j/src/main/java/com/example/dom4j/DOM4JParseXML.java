package com.example.dom4j;

import com.example.common.Book;
import com.example.common.Path;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : jeymingwu
 * @date : 2021-06-01
 **/

public class DOM4JParseXML<T> {

    private List<T> list;
    private T t;
    private Class<?> aClass;

    public DOM4JParseXML(String className) throws ClassNotFoundException {
        this.list = new ArrayList<>();
        this.aClass = Class.forName(className);
    }

    public List<T> xmlToObject(String path) throws DocumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(path);

        Element rootElement = document.getRootElement();

        Iterator<Element> elementIterator = rootElement.elementIterator();

        while (elementIterator.hasNext()) {
            System.out.println("========= DOM4J 开始遍历 =========");

            Element element = elementIterator.next();

            t = (T) aClass.newInstance();

            // 获取节点属性名与属性值
            List<Attribute> attributes = element.attributes();
            for (Attribute attribute:attributes) {
                System.out.println("属性名：" + attribute.getName() + " 属性值：" + attribute.getValue());
                this.setValue(attribute.getName(), attribute.getValue());
            }

            // 获取子节点
            Iterator<Element> childElementIterator = element.elementIterator();
            while (childElementIterator.hasNext()) {
                Element childElement = childElementIterator.next();
                System.out.println("节点名：" + childElement.getName() + " 节点值：" + childElement.getStringValue());
                this.setValue(childElement.getName(), childElement.getStringValue());
            }

            this.list.add(t);

            System.out.println("========= DOM4J 结束遍历 =========");
        }

        return this.list;
    }

    private void setValue(String name, String value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Field[] declaredFields = this.aClass.getDeclaredFields();

        for (Field field:declaredFields) {
            if (field.getName().equals(name)) {

                String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = aClass.getMethod(methodName, String.class);
                method.invoke(this.t, value);

            }
        }
    }

    public static void main(String[] args) {
        try {
            DOM4JParseXML<Book> bookDOM4JParseXML = new DOM4JParseXML<>(Book.class.getName());

            List<Book> books = bookDOM4JParseXML.xmlToObject(Path.getXMLPath());

            for (Book book:books) {
                System.out.println(book);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
