package com.example.common;

/**
 * @author : jeymingwu
 * @date : 2021-05-28
 **/

public class Path {

    public static String getXMLPath() {
        return Thread.currentThread().getContextClassLoader().getResource("Book.xml").getPath();
    }

    public static void main(String[] args) {
        System.out.println(Path.getXMLPath());
    }
}
