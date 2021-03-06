# xml-practice-demo
XML 解析与生成练习 Demo

## XML 解析

四种方式：DOM、SAX、JDOM、DOM4J，前两种属于由官方提供与平台无关的解析方式，后两种属于扩展方法；

### DOM 解析

+ DOM：document object model，文档对象模型；基于 DOM 的 XML 分析器将一个 XML 文档转换成一个对象模型集合（DOM 树），通过对对象模型的操作实现对 XML 文档数据的操作；
+ 随机访问机制：可通过 DOM 接口随时访问 XML 中任一部分数据；（强大灵活性）
+ DOM 前置使用树模型来访问 XML 文档中的信息；
+ 由于 DOM 的强大灵活性，需要 DOM 分析器将整个 XML 文档转换成 DOM 树存放在内存中；（一次性读取，对机器性能要求高，特别是结构复杂的树）
+ 总结：
    + 一次性加载 XML 所有数据至内存形成树结构，可随机读取；
    + 优点：树结构，灵活性强，更好理解、掌握；可随机读取；
    + 缺点：一次性读取，消耗较多内存，易造成内存溢出；对机器性能要求高；

[DOM 解析 XML Demo](./dom/src/main/java/com/example/dom/DOMParseXML.java)

### SAX 解析

+ SAX：Simple APIs For XML，XML 简单应用程序接口；
+ 顺序访问机制，一种快速读写 XML 文件数据的方式；
+ 当 SAX 分析器对 XML 文档进行分析时，会触发一系列事件并激活相应的事件处理函数；
+ 总结：
    + 流模型中的“推”模型分析方式；通过事件驱动，每发现一个节点就触发一个事件；
    + 优点：事件驱动模式，可立即开始分析，可随时停止，消耗较少内存；
    + 缺点：编码复杂；单向导航，无法定位文档层次，较难处理同时访问 XML 中多处不同的数据；

[SAX 解析 XML Demo](./sax/src/main/java/com/example/sax/SAXParseXML.java)

### JDOM 解析

+ JDOM：Java-based Document Object Model，Java 特定的文档对象模型；
+ 自身不包含 XML 解析器，使用 SAX 解析；
+ 需导入 JDOM 依赖包；
+ 总结：
  + 无解析器的解析；以 DOM 方式 SAX 内核解析 XML；
  + 优点：使用具体类简化了 DOM 的 API；大量使用 Java 集合；
  + 缺点：灵活性较差；性能较差；

[JDOM 解析 XML Demo](./jdom/src/main/java/com/example/jdom/JDOMParseXML.java)

### DOM4J 解析

+ DOM4J：Document Object Model For Java；
+ 简单易用，采用 Java 集合框架，并完全支持 DOM、SAX等；
+ 需导入 DOM4J 依赖包；
+ 总结：
  + 性能较优的实现；
  + 优点：大量使用 Java 集合框架、提供高性能的方法；支持 XPATH；性能优异、灵活性好、功能强大、终端易用等；
  + 缺点：大量使用抽象类和接口表示，API 复杂；

[DOM4J 解析 XML Demo](./dom4j/src/main/java/com/example/dom4j/DOM4JParseXML.java) 

## XML 解析总结：

+ DOM 适用于内容较小的 XML 文档，可随机读取，牺牲内存大小换取高灵活性；内容较大的 XML 文档并不适合，因其一次性全部加载的特性，易造成内存溢出，机器卡顿等；
+ SAX 顺序访问，事件驱动解析；适合内容较大的 XML 文档；顺序访问机制，造成只能单向遍历，无法直接定位；
+ JDOM 适用于内容较小的 XML 文档，（个人理解：DOM 和 SAX 的结合体，以 Java 集合框架的形式改进）使用 SAX 解析器解析，通过 Java 集合框架简化 DOM 的 API；
+ DOM4J 适用于内容较大的 XML 文档，业界较流行的解析 XML 的方式；性能优异、灵活性好、功能强大等优点；其内部实现较复杂；

参考链接：

+ [XML 解析与生成的四种实现方式](https://gitee.com/jinmingwu/Java-XML-Demo)
+ [XML解析——Java中XML的四种解析方式](https://www.cnblogs.com/longqingyang/p/5577937.html)
+ [JAVA解析xml的五种方式比较](https://www.cnblogs.com/longqingyang/p/5577947.html)