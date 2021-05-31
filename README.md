# xml-practice-demo
XML 解析与生成练习 Demo

## XML 解析

四种方式：DOM、SAX、JDOM、DOM4J，前两种属于由官方提供与平台无关的解析方式，后两种属于扩展方法；

### DOM 解析

+ DOM：document object model，文档对象模型；基于 DOM 的 XML 分析器将一个 XML 文档转换成一个对象模型集合（DOM 树），通过对对象模型的操作实现对 XML 文档数据的操作；
+ 随机访问机制：可通过 DOM 接口随时访问 XML 中任一部分数据；（强大灵活性）
+ DOM 前置使用树模型来访问 XML 文档中的信息；
+ 由于 DOM 的强大灵活性，需要 DOM 分析器将整个 XML 文档转换成 DOM 树存放在内存中；（一次性读取，对机器性能要求高，特别是结构复杂的树）

[DOM 解析 XML Demo](./dom/src/main/java/com/example/dom/ParseXML.java)

### SAX 解析

### JDOM 解析

### DOM4J 解析

