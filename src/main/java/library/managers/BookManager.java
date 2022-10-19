package library.managers;

import library.model.Book;
import library.repository.BookRepository;

import java.util.List;

public class BookManager {
    BookRepository bookRepository;

    public BookManager(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBook(String serialNumber) {
        return bookRepository.findBySerialNumber(serialNumber);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public void registerBook(String title, String author, String serialNumber, String genre) {
        bookRepository.add(new Book(title,author,serialNumber,genre));
    }

    public void unregisterBook(String serialNumber) {
        bookRepository.updateIsArchiveBySerialNumber(serialNumber,true); // dodac warunki
    }

}
