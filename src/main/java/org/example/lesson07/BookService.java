package org.example.lesson07;

public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public String findTitleOrDefault(String isbn) {
        // DONE 2: 使用 map 和 orElse 返回标题或默认值。

        return repository.findByIsbn(isbn).map(Book::getTitle).orElse("Unknown book");
    }

    public Book requireBook(String isbn) {
        // DONE 3: 使用 orElseThrow 返回书籍或抛出 IllegalArgumentException。

        return repository.findByIsbn(isbn).orElseThrow(()->
            new IllegalArgumentException("Book not found: " + isbn)
        );
    }
}
