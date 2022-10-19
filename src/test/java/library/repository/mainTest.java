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

    private ClientManager clientManager;

    private BookManager bookManager;

    private RentManager rentManager;

    @BeforeEach
    void beforeEach() {
        if (emf != null) {
            emf.close();
        }
        emf = Persistence.createEntityManagerFactory("POSTGRES");
        em = emf.createEntityManager();
        this.clientManager = new ClientManager(em);
        this.bookManager = new BookManager(em);
        this.rentManager = new RentManager(em);
    }

    @Test
    void addSameObjects() throws Exception {
        ClientManager clientManager = new ClientManager(em);
        BookManager bookManager = new BookManager(em);
        RentManager rentManager = new RentManager(em);
        clientManager.registerClient("imie", "naziwsko", "231312341", 45);
        clientManager.registerClient("dziecko", "naziwsko", "2313123341", 13);

        bookManager.registerBook("najlepssza ksiega", "najlepszy autor", "2131341", "genre");
        rentManager.rentBook("2313123341", "2131341");
        assertThrows(Exception.class, () -> {
            rentManager.rentBook("2313123341", "2131341");
        });
        assertThrows(Exception.class, () -> {
            bookManager.registerBook("najlepssza ksiega", "najlepszy autor", "2131341", "genre");
        });
        assertThrows(Exception.class, () -> {
            clientManager.registerClient("dziecko", "naziwsko", "2313123341", 13);
        });
    }
    @Test
    void addCheckRegister() throws Exception {
        ClientManager clientManager = new ClientManager(em);
        BookManager bookManager = new BookManager(em);
        RentManager rentManager = new RentManager(em);
        var client1 = clientManager.registerClient("imie", "naziwsko", "231312341", 45);
        var client2 = clientManager.registerClient("dziecko", "naziwsko", "2313123341", 13);
        var book1 = bookManager.registerBook("najlepssza ksiega", "najlepszy autor", "2131341", "genre");
        var rent1 = rentManager.rentBook("2313123341", "2131341");
        assertEquals(clientManager.getClient("231312341"),client1);
        assertEquals(clientManager.getClient("2313123341"),client2);
        assertEquals(bookManager.getBook("2131341"),book1);
        assertEquals(rentManager.getRentByBook("2131341"),rent1);
    }


}
