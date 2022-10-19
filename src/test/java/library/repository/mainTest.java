package library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;
import library.managers.BookManager;
import library.managers.ClientManager;
import library.managers.RentManager;
import library.model.Book;
import library.model.Child;
import library.model.Client;
import library.model.Rent;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class mainTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private ClientRepository clientRepository;
    private RentRepository rentRepository;
    private BookRepository bookRepository;

    @BeforeEach
    void beforeEach() {
        if (emf != null) {
            emf.close();
        }
        emf = Persistence.createEntityManagerFactory("POSTGRES");
        em = emf.createEntityManager();
        this.bookRepository = new BookRepository(em);
        this.clientRepository = new ClientRepository(em);
        this.rentRepository = new RentRepository(em);

    }

    @Test
    void essa() throws Exception {
        ClientManager clientManager = new ClientManager(clientRepository);
        BookManager bookManager = new BookManager(bookRepository);
        RentManager rentManager = new RentManager(rentRepository, clientRepository, bookRepository);
        clientManager.registerClient("imie", "naziwsko", "231312341", 45);
        clientManager.registerClient("dziecko", "naziwsko", "2313123341", 13);
        clientManager.registerClient("dziecko", "naziwsko", "2313123341", 13);

        bookManager.registerBook("najlepssza ksiega", "najlepszy autor", "2131341", "zjebane");
        rentManager.rentBook("2313123341", "2131341");
        assertThrows(Exception.class, () -> {
            rentManager.rentBook("2313123341", "2131341");
        });
        assertThrows(Exception.class, () -> {
            bookManager.registerBook("najlepssza ksiega", "najlepszy autor", "2131341", "zjebane");
        });
        assertThrows(Exception.class, () -> {
            clientManager.registerClient("dziecko", "naziwsko", "2313123341", 13);
        });
    }


}
