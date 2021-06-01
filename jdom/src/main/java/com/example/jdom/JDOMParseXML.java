package com.example.jdom;

import com.example.common.Book;
import com.example.common.Path;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : jeymingwu
 * @date : 2021-05-31
 **/

public class JDOMParseXML<T> {

    private List<T> list;

    private Object object;

    public JDOMParseXML() {
        this.list = new ArrayList<>();
    }

    public List<T> xmlToObjectByTagName(String path, String className) throws IOException, JDOMException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(path);

        Element rootElement = document.getRootElement();

        List<Element> elementChildren = rootElement.getChildren();

        for (Element element:elementChildren) {

            Class<?> aClass = Class.forName(className);
            this.object = aClass.newInstance();

            System.out.println("========= 开始解析第 " + (elementChildren.indexOf(element) + 1) + " 个节点 =========");

            List<Attribute> attributes = element.getAttributes();
            for (Attribute attribute:attributes) {
                String attrName = attribute.getName();
                String attrValue = attribute.getValue();
                System.out.println("属性名： " + attrName + " 属性值： " + attrValue);
                this.setValue(attrName, attrValue);
            }

            List<Element> children = element.getChildren();
            for (Element elementChild:children) {
                String name = elementChild.getName();
                String value = elementChild.getValue();
                System.out.println("属性名： " + name + " 属性值： " + value);
                this.setValue(name, value);
            }

            System.out.println("========= 结束解析第 " + (elementChildren.indexOf(element) + 1) + " 个节点 =========");

            this.list.add((T) this.object);
        }

        return this.list;
    }

    public void setValue(String name, String value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Field[] declaredFields = this.object.getClass().getDeclaredFields();

        for (Field field:declaredFields) {

            if (field.getName().equals(name)) {

                String methodName = "set" + name.substring(0, 1).toUpperCase()
                        + name.substring(1);

                Method method = object.getClass().getMethod(methodName, String.class);
                method.invoke(this.object, value);

            }
        }
    }

    public static void main(String[] args) {

        JDOMParseXML<Book> bookJDOMParseXML = new JDOMParseXML<>();

        try {
            List<Book> books = bookJDOMParseXML.xmlToObjectByTagName(Path.getXMLPath(), Book.class.getName());

            for (Book book:books) {
                System.out.println(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
