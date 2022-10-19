package library.managers;

import jakarta.persistence.EntityManager;
import library.model.Book;
import library.repository.BookRepository;

import java.util.List;

public class BookManager {
    BookRepository bookRepository;

    public BookManager(EntityManager entityManager) {
        this.bookRepository = new BookRepository(entityManager);
    }

    public Book getBook(String serialNumber) {
        return bookRepository.findBySerialNumber(serialNumber);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book registerBook(String title, String author, String serialNumber, String genre) {
        return bookRepository.add(new Book(title,author,serialNumber,genre));
    }

    public void unregisterBook(String serialNumber) {
        bookRepository.updateIsArchiveBySerialNumber(serialNumber,true); // dodac warunki
    }

}
