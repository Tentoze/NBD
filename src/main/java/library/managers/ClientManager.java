package library.managers;

import jakarta.persistence.EntityManager;
import library.model.Adult;
import library.model.Child;
import library.model.Client;
import library.repository.ClientRepository;

import java.util.List;

public class ClientManager {
    private ClientRepository clientRepository;

    public ClientManager(EntityManager entityManager) {
        this.clientRepository = new ClientRepository(entityManager);
    }

    public Client getClient(String personalID) {
        return clientRepository.findByPersonalID(personalID);
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    public Client registerClient(String firstname, String lastname, String personalID, int age) {
        if (age < 18) {
            return clientRepository.add(new Child(firstname, lastname, personalID, age));
        } else {
            return clientRepository.add(new Adult(firstname, lastname, personalID, age));
        }
    }

    public void unregisterClient(String personalID){
        clientRepository.updateIsArchiveByPersonalID(personalID,true); // dodac warunek czy oddal wszystkie ksiazki
        // i splacil debet
    }
}
