package com.example.sax;

import com.example.common.Book;
import com.example.common.Path;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

/**
 * @author : jeymingwu
 * @date : 2021-05-31
 **/

public class SAXParseXML<T> {

    public List<T> xmlToObjectByTagName(String path, String rootName, String elementName, String className) throws ParserConfigurationException, SAXException, ClassNotFoundException, IOException {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        SAXParser saxParser = saxParserFactory.newSAXParser();

        SAXParseHandler<T> handler = new SAXParseHandler<>(rootName, elementName, className);

        saxParser.parse(path, handler);

        return handler.getList();
    }

    public static void main(String[] args) {
        SAXParseXML<Book> bookParseXML = new SAXParseXML<>();

        try {
            List<Book> books = bookParseXML.xmlToObjectByTagName(Path.getXMLPath(), "bookStore", "book", Book.class.getName());

            for (Book book: books) {
                System.out.println(book);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
