package org.example.lesson19;

// record 适合表示只承载数据的对象，类似 TypeScript 中的只读数据类型。
// Java 会自动生成构造方法、id()、name()、equals()、hashCode() 和 toString()。
public record Product(String id, String name) {
}
