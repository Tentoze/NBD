package library.managers;

import library.model.Book;
import library.model.Client;
import library.model.Rent;
import library.repository.BookRepository;
import library.repository.ClientRepository;
import library.repository.RentRepository;

import java.util.Date;

public class RentManager {
    private RentRepository rentRepository;
    private ClientRepository clientRepository;
    private BookRepository bookRepository;

    public RentManager(RentRepository rentRepository, ClientRepository clientRepository, BookRepository bookRepository) {
        this.rentRepository = rentRepository;
        this.clientRepository = clientRepository;
        this.bookRepository = bookRepository;
    }

    public void rentBook(String personalID, String serialNumber) throws Exception {
        try {
            Client client = clientRepository.findByPersonalID(personalID);
            Book book = bookRepository.findBySerialNumber(serialNumber);
            checkIfBookCanBeRented(client, book);
            rentRepository.add(new Rent(client, book));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkIfBookCanBeRented(Client client, Book book) throws Exception {
        if (client.isArchive()) {
            throw new Exception("Client is archived");
        }
        if (book.isArchive()) {
            throw new Exception("Book is archived");
        }
        if (rentRepository.existsByBook(book)) {
            throw new Exception("Book is already rented");
        }
        if (client.getMaxBooks() < rentRepository.countByClient(client) + 1) {
            throw new Exception("Client have already rented max number of books");
        }
        if (client.getDebt() != 0) {
            throw new Exception("Client have to pay the debt");
        }
    }

    public void returnBook(String serialNumber) {
        try {
            Book book = bookRepository.findBySerialNumber(serialNumber);
            if (book == null) {
                throw new Exception("There is no book with that serial number");
            }
            Rent rent = rentRepository.findByBook(book);
            if (rent == null) {
                throw new Exception("There is no rent with this book");
            }
            if(isAfterEndTime(rent)){
                clientRepository.updateDebtByPersonalID(
                        rent.getClient().getPersonalID()
                        ,rent.getClient().getPenalty()+rent.getClient().getDebt());
            }
            rentRepository.remove(rent);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isAfterEndTime(Rent rent){
        Date date = new Date();
        if(date.after(rent.getEndTime())){
            return true;
        }
        return false;
    }

}
