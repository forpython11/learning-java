package org.example.lesson07;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookRepository {
    private final Map<String, Book> books = new HashMap<>();

    public void save(Book book) {
        books.put(book.getIsbn(), book);
    }

    public Optional<Book> findByIsbn(String isbn) {
        // DONE 1: 使用 Optional.ofNullable 包装 books.get(isbn)。
        Book book = books.get(isbn);
        return Optional.ofNullable(book);
    }
}
