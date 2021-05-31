package com.example.dom;

import com.example.common.Book;
import com.example.common.Path;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : jeymingwu
 * @date : 2021-05-28
 **/

public class DOMParseXML<T> {

    private static DocumentBuilderFactory factory;
    private static StringBuilder sb;

    static {
        factory = DocumentBuilderFactory.newInstance();
        sb = new StringBuilder();
    }

    public void xmlToObjectByTagName(String path, String tagName, String className, List<T> list) {
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(path);
            NodeList elementsByTagName = document.getElementsByTagName(tagName);

            System.out.println("TagName's number is :" + elementsByTagName.getLength());

            for (int i = 0; i < elementsByTagName.getLength(); ++i) {
                System.out.println("========== start to traverse No." + (i + 1) +  " elements ============");

                Node node = elementsByTagName.item(i);

                Class<?> aClass = Class.forName(className);
                Object o = aClass.newInstance();

                // 获取节点的所有属性集合
                NamedNodeMap nodeAttributes = node.getAttributes();
                System.out.println("The node has " + nodeAttributes.getLength() + " attributes");

                for (int j = 0; j < nodeAttributes.getLength(); ++j) {
                    Node item = nodeAttributes.item(j);
                    System.out.println("NodeName: " + item.getNodeName() + " NodeValue:" + item.getTextContent());

                    String methodName = sb.delete(0, sb.length()).append("set")
                            .append(item.getNodeName().substring(0, 1).toUpperCase())
                            .append(item.getNodeName().substring(1)).toString();

                    Method method = aClass.getMethod(methodName, String.class);
                    method.invoke(o, item.getTextContent());

                }

                // 获取节点的子节点
                NodeList childNodes = node.getChildNodes();
                System.out.println("The node has " + childNodes.getLength() + " childNodes");

                for (int j = 0; j < childNodes.getLength(); ++j) {
                    Node item = childNodes.item(j);

                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                        System.out.println("NodeName: " + item.getNodeName() + " NodeValue: " + item.getTextContent());

                        String methodName = sb.delete(0, sb.length()).append("set")
                                .append(item.getNodeName().substring(0, 1).toUpperCase())
                                .append(item.getNodeName().substring(1)).toString();

                        Method method = aClass.getMethod(methodName, String.class);
                        method.invoke(o, item.getTextContent());
                    }
                }


                list.add((T) o);
                System.out.println("========== end to traverse No." + (i + 1) +  " elements ============");
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DOMParseXML<Book> parseXML = new DOMParseXML();

        ArrayList<Book> books = new ArrayList<Book>();

        parseXML.xmlToObjectByTagName(Path.getXMLPath(), "book", Book.class.getName(), books);

        for (Book book:books) {
            System.out.println(book);
        }

    }

}
