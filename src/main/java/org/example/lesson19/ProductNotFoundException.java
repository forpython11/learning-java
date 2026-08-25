package org.example.lesson19;

// 自定义异常类型，专门表达“根据 ID 没有找到商品”这一业务错误。
// 继承 RuntimeException 后，调用方不需要在方法签名中声明 throws。
public class ProductNotFoundException extends RuntimeException {
    // 接收具体错误消息，例如 "Product not found: P999"。
    public ProductNotFoundException(String message) {
        // 把消息交给父类保存，之后可以通过 exception.getMessage() 读取。
        super(message);
    }
}
